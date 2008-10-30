/*
 * $Id$
 *
 * Copyright  1997-2004 Unidata Program Center/University Corporation for
 * Atmospheric Research, P.O. Box 3000, Boulder, CO 80307,
 * support@unidata.ucar.edu.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package edu.wisc.ssec.mcidasv.data;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ucar.unidata.data.AddeUtil;
import ucar.unidata.data.DataChoice;
import ucar.unidata.data.DataSelection;
import ucar.unidata.data.DataSourceDescriptor;
import ucar.unidata.util.LogUtil;
import ucar.unidata.util.Misc;
import ucar.unidata.util.Trace;
import ucar.unidata.util.TwoFacedObject;

import visad.*;
import ucar.unidata.geoloc.LatLonRect;
import edu.wisc.ssec.mcidas.adde.AddePointURL;
import ucar.visad.quantities.CommonUnits;


/**
 * A data source for ADDE point data
 *
 * @author Don Murray
 * @version $Revision$ $Date$
 */
public class AddePointDataSource extends ucar.unidata.data.point.AddePointDataSource {

    /** list of levels names */
    private static String[] levelNames = {
        "SFC", "1000", "925", "850", "700", "500", "400", "300", "250", "200",
        "150", "100", "70", "50", "30", "20", "10"
    };

    /** list of level values */
    private static int[] levelValues = {
        1001, 1000, 925, 850, 700, 500, 400, 300, 250, 200,
        150, 100, 70, 50, 30, 20, 10
    };

    /**
     * Default constructor.
     *
     * @throws VisADException
     */
    public AddePointDataSource() throws VisADException {
        super();
    }

    /**
     * Create a new <code>AddePointDataSource</code> from the parameters
     * supplied.
     *
     * @param descriptor  <code>DataSourceDescriptor</code> for this.
     * @param source      Source URL
     * @param properties  <code>Hashtable</code> of properties for the source.
     *
     * @throws VisADException  couldn't create the VisAD data
     */
    public AddePointDataSource(DataSourceDescriptor descriptor,
                               String source, Hashtable properties)
            throws VisADException {
        super(descriptor, source, properties);
    }

    /**
     * Get the list of all levels available from this DataSource
     *
     *
     * @param dataChoice The data choice we are getting levels for
     * @return  List of all available levels
     */
    public List getAllLevels(DataChoice dataChoice, DataSelection dataSelection) {
        return getLevels();
    }

    /**
     * Get the levels property
     * @return levels;
     */
    private List getLevels() {
    	List levels = new ArrayList();
    	try {
    		for (int i = 0; i < levelValues.length; i++) {
    			levels.add(new TwoFacedObject(levelNames[i],
    					new Real(RealType.getRealType("Pressure",
    							CommonUnits.MILLIBAR), levelValues[i],
    							CommonUnits.MILLIBAR)));
    		}
    	} catch (VisADException ve) {}
    	return levels;
    }

}

