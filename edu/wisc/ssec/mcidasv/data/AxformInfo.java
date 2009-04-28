package edu.wisc.ssec.mcidasv.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AxformInfo extends HeaderInfo {

	/** The url */
	private String dataFile = "";
	private boolean isAxform = false;

	/**
	 * Ctor for xml encoding
	 */
	public AxformInfo() {}

	/**
	 * CTOR
	 *
	 * @param filename The filename
	 */
	public AxformInfo(File thisFile) {
		this(thisFile.getAbsolutePath());
	}

	/**
	 * CTOR
	 *
	 * @param filename The filename
	 */
	public AxformInfo(String filename) {
		super(filename);
	}

	/**
	 * Is the file an AXFORM header file?
	 */
	public boolean isAxformInfoHeader() {
		parseHeader();
		return isAxform;
	}

	/**
	 * Parse a potential AXFORM header file
	 */
	protected void parseHeader() {
		if (haveParsed()) return;
		if (!doesExist()) {
			isAxform = false;
			return;
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(getFilename()));
			int lineNum = 0;
			String line;
			String description = "";
			boolean gotFiles = false;
			
			List bandNames = new ArrayList();
			List bandFiles = new ArrayList();
			
			File latFile = null;
			File lonFile = null;
			File thisFile = new File(getFilename());
			String parent = thisFile.getParent();
			if (parent==null) parent=".";

			while ((line = br.readLine()) != null) {
				lineNum++;
				if (line.trim().equals("Space Science & Engineering Center")) {
					isAxform = true;
					continue;
				}
				if (!isAxform) break;

				if (line.length() < 80) {
					if (lineNum > 15) gotFiles = true;
					continue;
				}

				// Process the description from lines 5 and 6
				if (lineNum==5) {
					description += line.substring(13, 41).trim();
				}
				else if (lineNum==6) {
					description += " " + line.substring(14, 23).trim() +" " + line.substring(59, 71).trim();
					setParameter(DESCRIPTION, description);
				}

				// Process the file list starting at line 15
				else if (lineNum>=15 && !gotFiles) {
					String parameter = line.substring(0, 13).trim();
					if (parameter.equals("Header")) {
						isAxform = true;
					}
					else if (parameter.equals("Latitude")) {
						latFile = new File(parent + "/" + line.substring(66).trim());
					}
					else if (parameter.equals("Longitude")) {
						lonFile = new File(parent + "/" + line.substring(66).trim());
					}
					else {
						//TODO: "parameter" here is actually raw, brightness, etc...
						//      deal with it appropriately when creating the data source
//						if (parameter.equals("Raw Sensor")) unitType = 0;
//						else if (parameter.equals("Brightness")) unitType = 1;
						setParameter(LINES, Integer.parseInt(line.substring(24, 31).trim()));
						setParameter(ELEMENTS, Integer.parseInt(line.substring(32, 40).trim()));
						String band = line.substring(19, 23).trim();
						String format = line.substring(41, 59).trim();
						System.out.println("looking at format line: " + format);
						if (format.indexOf("ASCII") >= 0) {
							setParameter(DATATYPE, kFormatASCII);
						}
						else if (format.indexOf("8 bit") >= 0) {
							setParameter(DATATYPE, kFormat1ByteUInt);
						}
						else if (format.indexOf("16 bit") >= 0) {
							setParameter(DATATYPE, kFormat2ByteSInt);
						}
						else if (format.indexOf("32 bit") >= 0) {
							setParameter(DATATYPE, kFormat4ByteSInt);
						}
						String filename = line.substring(66).trim();
						filename = parent + "/" + filename;
						bandFiles.add(filename);
						bandNames.add("Band " + band);
					}
				}

				// Look for the missing value, bail when you find it
				else if (gotFiles) {
					if (line.indexOf("Navigation files missing data value") >= 0) {
						setParameter(MISSINGVALUE, Float.parseFloat(line.substring(44, 80).trim()));    			
						break;
					}
				}

				setParameter(BANDNAMES, bandNames);
				setParameter(BANDFILES, bandFiles);
				
				if (latFile != null && lonFile != null) {
					List latlonFiles = new ArrayList();
					latlonFiles.add(latFile);
					latlonFiles.add(lonFile);
					setParameter(NAVFILES, latlonFiles);
				}

			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}