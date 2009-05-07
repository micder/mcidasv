package edu.wisc.ssec.mcidasv.servermanager;

import static ucar.unidata.xml.XmlUtil.findChildren;
import static ucar.unidata.xml.XmlUtil.getAttribute;

import static edu.wisc.ssec.mcidasv.util.CollectionHelpers.arrList;
import static edu.wisc.ssec.mcidasv.util.CollectionHelpers.map;
import static edu.wisc.ssec.mcidasv.util.CollectionHelpers.newLinkedHashSet;
import static edu.wisc.ssec.mcidasv.util.CollectionHelpers.newMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import ucar.unidata.idv.IdvResourceManager;
import ucar.unidata.idv.chooser.adde.AddeServer;
import ucar.unidata.util.IOUtil;
import ucar.unidata.util.LogUtil;
import ucar.unidata.util.StringUtil;

import edu.wisc.ssec.mcidasv.ResourceManager;
import edu.wisc.ssec.mcidasv.servermanager.RemoteAddeEntry.EntrySource;
import edu.wisc.ssec.mcidasv.servermanager.RemoteAddeEntry.EntryStatus;
import edu.wisc.ssec.mcidasv.servermanager.RemoteAddeEntry.EntryType;
import edu.wisc.ssec.mcidasv.util.Contract;
import edu.wisc.ssec.mcidasv.util.functional.Function;

// useful methods for doing things like converting a "AddeServer" to a "RemoteAddeEntry"
// and so on.
public class EntryTransforms {
    private static final Pattern routePattern = Pattern.compile("^ADDE_ROUTE_(.*)=(.*)$");
    private static final Pattern hostPattern = Pattern.compile("^HOST_(.*)=(.*)$");

    private static final Matcher routeMatcher = routePattern.matcher("");
    private static final Matcher hostMatcher = hostPattern.matcher("");

    private EntryTransforms() { }

    public static final Function<AddeServer, RemoteAddeEntry> convertIdvServer = new Function<AddeServer, RemoteAddeEntry>() {
        public RemoteAddeEntry apply(final AddeServer arg) {
            String hostname = arg.toString().toLowerCase();
            for (AddeServer.Group group : (List<AddeServer.Group>)arg.getGroups()) {
                System.err.println("apply: hostname="+hostname+" group="+group.getName()+" type="+group.getType());
            }

            return new RemoteAddeEntry.Builder(hostname, "temp").build();
        }
    };

    // converts a list of AddeServers to a set of RemoteAddeEntry
    public static Set<RemoteAddeEntry> convertIdvServers(final List<AddeServer> idvServers) {
        Set<RemoteAddeEntry> addeEntries = newLinkedHashSet();
        addeEntries.addAll(map(convertIdvServer, idvServers));
        return addeEntries;
    }

    /**
     * Converts the XML contents of {@link ResourceManager#RSC_NEW_USERSERVERS}
     * to a {@link Set} of {@link RemoteAddeEntry}s.
     * 
     * @param root {@literal "Root"} of the XML to convert.
     * 
     * @return {@code Set} of {@code RemoteAddeEntry}s described by 
     * {@code root}.
     */
    protected static Set<RemoteAddeEntry> convertUserXml(final Element root) {
        Set<RemoteAddeEntry> entries = newLinkedHashSet();
        // <entry name="SERVER/DATASET" user="ASDF" proj="0000" source="user" enabled="true" type="image"/>
        List<Element> elements = findChildren(root, "entry");
        for (Element entryXml : elements) {
            String name = getAttribute(entryXml, "name");
            String user = getAttribute(entryXml, "user");
            String proj = getAttribute(entryXml, "proj");
            String source = getAttribute(entryXml, "source");
            String type = getAttribute(entryXml, "type");

            boolean enabled = Boolean.parseBoolean(getAttribute(entryXml, "enabled"));

            EntryType entryType = strToEntryType(type);
            EntryStatus entryStatus = (enabled == true) ? EntryStatus.ENABLED : EntryStatus.DISABLED; 

            if (source.equals("user") && (name != null)) {
                String[] arr = name.split("/");
                String description = arr[0];
                if (arr[0].toLowerCase().contains("localhost")) {
                    description = "<LOCAL-DATA>";
                }

                RemoteAddeEntry.Builder incomplete = 
                    new RemoteAddeEntry.Builder(arr[0], arr[1])
                        .type(entryType)
                        .status(entryStatus)
                        .source(EntrySource.USER)
                        .description(description);

                if (((user != null) && (proj != null)) && ((user.length() > 0) && (proj.length() > 0)))
                    incomplete = incomplete.account(user, proj);

                entries.add(incomplete.build());
            }
        }

        return entries;
    }

    /**
     * Converts the XML contents of {@link IdvResourceManager#RSC_ADDESERVER} 
     * to a {@link Set} of {@link RemoteAddeEntry}s.
     * 
     * @param root XML to convert.
     * @param source Used to {@literal "bulk set"} the origin of whatever
     * {@code RemoteAddeEntry}s get created.
     * 
     * @return {@code Set} of {@code RemoteAddeEntry}s contained within 
     * {@code root}.
     */
    @SuppressWarnings("unchecked")
    protected static Set<RemoteAddeEntry> convertAddeServerXml(Element root, EntrySource source) {
        Set<RemoteAddeEntry> es = newLinkedHashSet();

        List<Element> serverNodes = findChildren(root, "server");
        for (int i = 0; i < serverNodes.size(); i++) {
            Element element = (Element)serverNodes.get(i);
            String address = getAttribute(element, "name");
            String description = getAttribute(element, "description", "");

            // loop through each "group" entry.
            List<Element> groupNodes = findChildren(element, "group");
            for (int j = 0; j < groupNodes.size(); j++) {
                Element group = (Element)groupNodes.get(j);

                // convert whatever came out of the "type" attribute into a 
                // valid EntryType.
                String strType = getAttribute(group, "type");
                EntryType type = strToEntryType(strType);

                // the "names" attribute can contain comma-delimited group
                // names.
                List<String> names = StringUtil.split(getAttribute(group, "names", ""), ",", true, true);
                for (String name : names) {
                    if (name.length() == 0)
                        continue;

                    RemoteAddeEntry e =  new RemoteAddeEntry
                                            .Builder(address, name)
                                            .source(source)
                                            .type(type)
                                            .description(description)
                                            .build();
                    es.add(e);
                }

                // there's also an optional "name" attribute! woo!
                String name = getAttribute(group, "name", (String) null);
                if ((name != null) && (name.length() > 0)) {

                    RemoteAddeEntry e = new RemoteAddeEntry
                                            .Builder(address, name)
                                            .source(source)
                                            .description(description)
                                            .build();
                    es.add(e);
                }

                // anything else?
            }
        }
        return es;
    }

    /**
     * Attempts to convert a {@link String} to a {@link EntryType}.
     * 
     * @param s Value whose {@code EntryType} is wanted.
     * 
     * @return One of {@code EntryType}. If there was no {@literal "sensible"}
     * conversion, the method returns {@link EntryType#UNKNOWN}.
     */
    private static EntryType strToEntryType(final String s) {
        EntryType type = EntryType.UNKNOWN;
        Contract.notNull(s);
        try {
            type = EntryType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) { }
        return type;
    }

    // TODO(jon): re-add verify flag?
    protected static Set<RemoteAddeEntry> extractMctableEntries(final String path) {
        Set<RemoteAddeEntry> entries = newLinkedHashSet();

        try {
            InputStream is = IOUtil.getInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            Map<String, Set<String>> hosts = newMap();
            Map<String, String> hostToIp = newMap();
            Map<String, String> datasetToHost = newMap();

            // special case for an local ADDE entries.
            Set<String> blah = newLinkedHashSet();
            blah.add("LOCAL-DATA");
            hosts.put("LOCAL-DATA", blah);
            hostToIp.put("LOCAL-DATA", "LOCAL-DATA");

            while ((line = reader.readLine()) != null) {
                routeMatcher.reset(line);
                hostMatcher.reset(line);

                if (routeMatcher.find()) {
                    String dataset = routeMatcher.group(1);
                    String host = routeMatcher.group(2).toLowerCase();
                    datasetToHost.put(dataset, host);
                }
                else if (hostMatcher.find()) {
                    String name = hostMatcher.group(1).toLowerCase();
                    String ip = hostMatcher.group(2);

                    Set<String> nameSet = hosts.get(ip);
                    if (nameSet == null)
                        nameSet = newLinkedHashSet();

                    nameSet.add(name);
                    hosts.put(ip, nameSet);

                    hostToIp.put(name, ip);
                    hostToIp.put(ip, ip); // HACK :(
                }
            }

            Map<String, String> datasetsToIp = mapDatasetsToIp(datasetToHost, hostToIp);
            Map<String, String> ipToName = mapIpToName(hosts);
            List<RemoteAddeEntry> l = mapDatasetsToName(datasetsToIp, ipToName);
            entries.addAll(l);
            is.close();
        } catch (IOException e) {
            LogUtil.logException("Reading file: "+path, e);
        }

        return entries;
    }

    /**
     * This method is slightly confusing, sorry! Think of it kind of like a
     * {@literal "SQL JOIN"}... 
     * 
     * <p>Basically create {@link RemoteAddeEntry}s by using a hostname to
     * determine which dataset belongs to which IP.
     * 
     * @param datasetToHost {@code Map} of ADDE groups to host names.
     * @param hostToIp {@code Map} of host names to IP addresses.
     * 
     * @return
     */
    private static List<RemoteAddeEntry> mapDatasetsToName(
        final Map<String, String> datasetToHost, final Map<String, String> hostToIp) 
    {
        List<RemoteAddeEntry> entries = arrList();
        for(Entry<String, String> entry : datasetToHost.entrySet()) {
            String dataset = entry.getKey();
            String ip = entry.getValue();
            String name = ip;
            if (hostToIp.containsKey(ip))
                name = hostToIp.get(ip);

            RemoteAddeEntry e = new RemoteAddeEntry.Builder(name, dataset)
                                    .source(EntrySource.MCTABLE).build();
            entries.add(e);
        }
        return entries;
    }

    private static Map<String, String> mapIpToName(
        final Map<String, Set<String>> map) 
    {
        assert map != null;

        Map<String, String> ipToName = newMap();
        for (Entry<String, Set<String>> entry : map.entrySet()) {
            Set<String> names = entry.getValue();
            String displayName = "";
            for (String name : names)
                if (name.length() >= displayName.length())
                    displayName = name;

            if (displayName.equals(""))
                displayName = entry.getKey();

            ipToName.put(entry.getKey(), displayName);
        }
        return ipToName;
    }

    private static Map<String, String> mapDatasetsToIp(final Map<String, String> datasets, final Map<String, String> hostMap) {
        assert datasets != null;
        assert hostMap != null;

        Map<String, String> datasetToIp = newMap();
        for (Entry<String, String> entry : datasets.entrySet()) {
            String dataset = entry.getKey();
            String alias = entry.getValue();
            if (hostMap.containsKey(alias))
                datasetToIp.put(dataset, hostMap.get(alias));
        }
        return datasetToIp;
    }
}