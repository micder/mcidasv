<table cellspacing=0 style="border-top:1px solid black; border-bottom:1px solid black; width:300px; height:30px; margin:0px;">
<tr>
  <td class=selected id="tab_index" style="border:0px solid black; width:33%;">
    <a class="pointer" onClick="setView('index');">Index</a>
  </td>
  <td class=unselected id="tab_search" style="border:0px solid black; width:33%; border-left:1px solid black;">
    <a class="pointer" onClick="setView('search');">Search</a>
  </td>
  <td class=unselected id="tab_pdf" style="border:0px solid black; width:33%; border-left:1px solid black;">
    <a class="pointer" onClick="setView('pdf');">PDF</a>
  </td>
</tr>
</table>

<p>

<!-- INDEX -->
<div class="small menu" id="div_index">
  <span class="link" onClick="setPage('toc.html');">McIDAS-V User's Guide</span><br>

  <div class="indented" style="display:block;">
    <span class="link" onClick="setPage('mcidasv.html');">What is McIDAS-V?</span><br>
    <a class="toggle" onClick="toggleChildren(this,'overview');">+</a>
    <span class="link" onClick="setPage('page_overview.html');">Overview</span><br>

    <div class="indented" id="overview">
      <span class="link" onClick="setPage('ReleaseNotes.html');">Release Notes</span><br>
      <span class="link" onClick="setPage('Systems.html');">System Requirements</span><br>
      <span class="link" onClick="setPage('Starting.html');">Downloading and Running McIDAS-V</span><br>
      <span class="link" onClick="setPage('data/DataSources.html');">Data Formats and Sources</span><br>
      <span class="link" onClick="setPage('Support.html');">Documentation and Support</span><br>
      <span class="link" onClick="setPage('Faq.html');">FAQ</span><br>
      <span class="link" onClick="setPage('Bugs.html');">Known Problems</span><br>
      <span class="link" onClick="setPage('License.html');">License and Copyright</span><br>
    </div>

    <a class="toggle" onClick="toggleChildren(this,'gettingstarted');">+</a>
    <span class="link" onClick="setPage('quickstart/index.html');">Getting Started</span><br>

    <div class="indented" id="gettingstarted">
      <span class="link" onClick="setPage('quickstart/Satellite.html');">Animation Loops of Satellite Imagery</span><br>
      <span class="link" onClick="setPage('quickstart/RadarLevelIII.html');">An Animation Loop of NWS WSR-88D Level III Radar Imagery</span><br>
      <span class="link" onClick="setPage('quickstart/RadarLevelII.html');">Level II Radar</span><br>
      <span class="link" onClick="setPage('quickstart/Grids.html');">Plots from Gridded Data</span><br>
      <span class="link" onClick="setPage('quickstart/Surface.html');">Surface Observations - METAR and Synoptic Plots</span><br>
      <span class="link" onClick="setPage('quickstart/Upperair.html');">Upper Air Plots of RAOB Data</span><br>
      <span class="link" onClick="setPage('quickstart/Profiler.html');">Profiler Winds</span><br>
      <span class="link" onClick="setPage('quickstart/Globe.html');">Globe Display</span><br>
      <span class="link" onClick="setPage('quickstart/LocalFiles.html');">Creating a Display from Local Files </span><br>
      <span class="link" onClick="setPage('quickstart/UrlFiles.html');">Creating a Display from a URL </span><br>
      <span class="link" onClick="setPage('quickstart/DirectoryFiles.html');">Creating a Display from a Directory </span><br>
      <span class="link" onClick="setPage('quickstart/Hydra.html');">HYDRA</span><br>
      <span class="link" onClick="setPage('quickstart/Bridge.html');">McIDAS-X Bridge</span><br>
    </div>

    <a class="toggle" onClick="toggleChildren(this,'dataexplorer');">+</a>
    <span class="link" onClick="setPage('ui/DataExplorer.html');">Data Explorer</span><br>

    <div class="indented" id="dataexplorer">
      <a class="toggle" onClick="toggleChildren(this,'choosingdatasources');">+</a>
      <span class="link" onClick="setPage('data/choosers/index.html');">Choosing Data Sources</span><br>

      <div class="indented" id="choosingdatasources">
        <span class="link" onClick="setPage('data/choosers/FileChooser.html');">Choosing Data on Disk</span><br>
        <span class="link" onClick="setPage('data/choosers/UrlChooser.html');">Choosing a URL</span><br>
        <span class="link" onClick="setPage('data/choosers/CatalogChooser.html');">Choosing Cataloged Data</span><br>
        <span class="link" onClick="setPage('data/choosers/ImageChooser.html');">Choosing Satellite Imagery</span><br>
        <span class="link" onClick="setPage('data/choosers/RadarChooser.html');">Choosing NEXRAD Level III Radar Data</span><br>
        <span class="link" onClick="setPage('data/choosers/Level2Chooser.html');">Choosing NEXRAD Level II Radar Data</span><br>
        <span class="link" onClick="setPage('data/choosers/PointChooser.html');">Choosing Point Observation Data</span><br>
        <span class="link" onClick="setPage('data/choosers/RaobChooser.html');">Choosing Upper Air Data</span><br>
        <span class="link" onClick="setPage('data/choosers/ProfilerChooser.html');">Choosing NOAA National Profiler Network Data</span><br>
        <span class="link" onClick="setPage('data/choosers/DirectoryChooser.html');">Polling on files in a Directory</span><br>
        <span class="link" onClick="setPage('data/choosers/HydraChooser.html');">Choosing Multispectral Data</span><br>
        <span class="link" onClick="setPage('data/choosers/BridgeChooser.html');">Creating a McIDAS-X Bridge Session </span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'thefieldselector');">+</a>
      <span class="link" onClick="setPage('data/FieldSelector.html');">The Field Selector</span><br>

      <div class="indented" id="thefieldselector">
        <span class="link" onClick="setPage('data/DataSourceProperties.html');">Data Source Properties</span><br>
      </div>

    </div>

    <a class="toggle" onClick="toggleChildren(this,'mainwindow');">+</a>
    <span class="link" onClick="setPage('ui/index.html');">Main Window</span><br>

    <div class="indented" id="mainwindow">
      <span class="link" onClick="setPage('ui/Menus.html');">Menu Bar</span><br>
      <span class="link" onClick="setPage('ui/MainToolBar.html');">Main Toolbar</span><br>
      <span class="link" onClick="setPage('ui/DisplayMenus.html');">Display Menus </span><br>
      <span class="link" onClick="setPage('ui/Animation.html');">Time Animation</span><br>
      <span class="link" onClick="setPage('ui/Navigation.html');">Zooming, Panning and Rotating</span><br>
      <span class="link" onClick="setPage('ui/TransectViewManager.html');">Transect Views</span><br>
    </div>

    <a class="toggle" onClick="toggleChildren(this,'layercontrols');">+</a>
    <span class="link" onClick="setPage('page_layercontrols.html');">Layer Controls</span><br>

    <div class="indented" id="layercontrols">
      <a class="toggle" onClick="toggleChildren(this,'layercontrols_1');">+</a>
      <span class="link" onClick="setPage('controls/index.html');">Overview</span><br>

      <div class="indented" id="layercontrols_1">
        <span class="link" onClick="setPage('controls/index.html#legends');">Display Legends</span><br>
        <span class="link" onClick="setPage('controls/index.html#menus');">Menus</span><br>
        <span class="link" onClick="setPage('controls/index.html#properties');">Properties Dialog</span><br>
        <span class="link" onClick="setPage('controls/index.html#visibility');">Display Visibility</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_2');">+</a>
      <span class="link" onClick="setPage('page_griddeddatadisplays.html');">Gridded Data Displays</span><br>

      <div class="indented" id="layercontrols_2">
        <span class="link" onClick="setPage('controls/PlanViewControl.html');">Plan View Controls</span><br>
        <span class="link" onClick="setPage('controls/FlowPlanViewControl.html');">Flow Plan Controls</span><br>
        <span class="link" onClick="setPage('controls/CrossSectionControl.html');">Vertical Cross-section Controls</span><br>
        <span class="link" onClick="setPage('controls/ThreeDSurfaceControl.html');">Isosurface Controls</span><br>
        <span class="link" onClick="setPage('controls/VolumeRenderControl.html');">Volume Rendering Controls</span><br>
        <span class="link" onClick="setPage('controls/ValuePlotControl.html');">Value Plot Controls </span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_3');">+</a>
      <span class="link" onClick="setPage('page_satelliteandradardisplays.html');">Satellite and Radar Displays</span><br>

      <div class="indented" id="layercontrols_3">
        <span class="link" onClick="setPage('controls/ImagePlanViewControl.html');">Image Controls</span><br>
        <span class="link" onClick="setPage('controls/LevelIIIControl.html');">WSR-88D Level III Controls</span><br>
        <span class="link" onClick="setPage('page_level2radardisplaycontrols.html');">Level 2 Radar Layer Controls</span><br>
        <span class="link" onClick="setPage('controls/level2/RadarSweepControl.html');">Radar Sweep Controls</span><br>
        <span class="link" onClick="setPage('controls/level2/RhiControl.html');">RHI Display Controls </span><br>
        <span class="link" onClick="setPage('controls/level2/RadarVolumeControl.html');">Radar Volume Controls</span><br>
        <span class="link" onClick="setPage('controls/level2/RadarIsosurfaceControl.html');">Isosurface Controls </span><br>
        <span class="link" onClick="setPage('controls/misc/RadarGridControl.html');">Range Rings</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_4');">+</a>
      <span class="link" onClick="setPage('controls/profiler/index.html');">Profiler Controls</span><br>

      <div class="indented" id="layercontrols_4">
        <span class="link" onClick="setPage('controls/profiler/ProfilerTimeHeightControl.html');">Profiler Time/Height Controls</span><br>
        <span class="link" onClick="setPage('controls/profiler/ProfilerStationPlotControl.html');">Profiler Station Plot Controls</span><br>
        <span class="link" onClick="setPage('controls/profiler/ProfilerMultiStationControl3D.html');">Profiler 3D Multi-station Controls</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_5');">+</a>
      <span class="link" onClick="setPage('page_probes.html');">Probes</span><br>

      <div class="indented" id="layercontrols_5">
        <span class="link" onClick="setPage('controls/ProbeControl.html');">Data Probe/Time Series</span><br>
        <span class="link" onClick="setPage('controls/TimeHeightControl.html');">Time-Height Controls</span><br>
        <span class="link" onClick="setPage('controls/ProfileControl.html');">Vertical Profile Controls</span><br>
        <span class="link" onClick="setPage('controls/DataTransectControl.html');">Data Transect Controls</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_6');">+</a>
      <span class="link" onClick="setPage('page_mappingcontrols.html');">Mapping Controls</span><br>

      <div class="indented" id="layercontrols_6">
        <span class="link" onClick="setPage('controls/MapDisplayControl.html');">Map Controls</span><br>
        <span class="link" onClick="setPage('controls/TopographyControl.html');">Topography Controls</span><br>
        <span class="link" onClick="setPage('controls/ShapefileControl.html');">Shapefile Controls</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_7');">+</a>
      <span class="link" onClick="setPage('page_observationandlocationcontrols.html');">Observation and Location Controls</span><br>

      <div class="indented" id="layercontrols_7">
        <span class="link" onClick="setPage('controls/StationModelControl.html');">Point Data Plot</span><br>
        <span class="link" onClick="setPage('controls/ObsListControl.html');">Observation List Controls</span><br>
        <span class="link" onClick="setPage('controls/AerologicalSoundingControl.html');">Sounding Display</span><br>
        <span class="link" onClick="setPage('controls/StationLocationControl.html');">Location Controls</span><br>
        <span class="link" onClick="setPage('controls/WorldWindControl.html');">WorldWind Controls</span><br>
        <span class="link" onClick="setPage('controls/TrackControl.html');">Track Controls</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'layercontrols_8');">+</a>
      <span class="link" onClick="setPage('page_miscellaneouscontrols.html');">Miscellaneous Controls</span><br>

      <div class="indented" id="layercontrols_8">
        <span class="link" onClick="setPage('controls/misc/HydraControl.html');">HYDRA Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/BridgeControl.html');">McIDAS-X Bridge Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/RangeAndBearingControl.html');">Range and Bearing Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/DrawingControl.html');">Drawing Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/TransectDrawingControl.html');">Transect Drawing Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/LocationIndicatorControl.html');">Location Indicator Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/OmniControl.html');">Omni Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/MovieDisplay.html');">QuickTime Movie Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/WMSControl.html');">Web Map Server(WMS)/Background Image Controls</span><br>
        <span class="link" onClick="setPage('controls/JythonControl.html');">Jython Controls</span><br>
        <span class="link" onClick="setPage('controls/misc/TextProductControl.html');">Weather Text Product Controls</span><br>
      </div>

      <span class="link" onClick="setPage('controls/Chart.html');">Charts</span><br>

    </div>

    <a class="toggle" onClick="toggleChildren(this,'tools');">+</a>
    <span class="link" onClick="setPage('page_tools.html');">Tools</span><br>

    <div class="indented" id="tools">
      <a class="toggle" onClick="toggleChildren(this,'tools_1');">+</a>
      <span class="link" onClick="setPage('tools/preferences/Preferences.html');">User Preferences</span><br>

      <div class="indented" id="tools_1">
        <span class="link" onClick="setPage('tools/preferences/GeneralPreferences.html');">General Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/DisplayWindowPreferences.html');">Display Window Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/ToolbarPreferences.html');">Toolbar Option Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/DataPreferences.html');">Data Sources Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/ServerPreferences.html');">ADDE Server Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/AvailableDisplaysPreferences.html');">Available Displays Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/NavigationPreferences.html');">Navigation Control Preferences </span><br>
        <span class="link" onClick="setPage('tools/preferences/FormatPreferences.html');">Formats and Data Preferences</span><br>
        <span class="link" onClick="setPage('tools/preferences/AdvancedPreferences.html');">Advanced Preferences </span><br>
      </div>

      <span class="link" onClick="setPage('tools/LocalDataManager.html');">Local Data Manager</span><br>
      <span class="link" onClick="setPage('tools/ProjectionManager.html');">Projection Manager</span><br>
      <span class="link" onClick="setPage('tools/ColorTableEditor.html');">Color Table Editor</span><br>
      <span class="link" onClick="setPage('tools/StationModelEditor.html');">Layout Model Editor</span><br>
      <span class="link" onClick="setPage('tools/AliasEditor.html');">Parameter Alias Editor</span><br>
      <span class="link" onClick="setPage('tools/ParameterDefaultsEditor.html');">Parameter Defaults Editor</span><br>
      <span class="link" onClick="setPage('tools/ParameterGroupsEditor.html');">Parameter Groups Editor</span><br>
      <span class="link" onClick="setPage('tools/ContourDialog.html');">Contour Properties Editor</span><br>
      <span class="link" onClick="setPage('tools/ImageCaptures.html');">Image and Movie Capture</span><br>
      <span class="link" onClick="setPage('tools/Timeline.html');">Timeline</span><br>
      <span class="link" onClick="setPage('tools/Console.html');">Message Console</span><br>
      <span class="link" onClick="setPage('tools/SupportRequestForm.html');">Support Request Form</span><br>

    </div>

    <a class="toggle" onClick="toggleChildren(this,'miscellaneous');">+</a>
    <span class="link" onClick="setPage('page_miscellaneous.html');">Miscellaneous</span><br>

    <div class="indented" id="miscellaneous">
      <span class="link" onClick="setPage('Bundles.html');">Bundles</span><br>

      <a class="toggle" onClick="toggleChildren(this,'miscellaneous_1');">+</a>
      <span class="link" onClick="setPage('isl/index.html');">McIDAS-V Scripting</span><br>

      <div class="indented" id="miscellaneous_1">
        <span class="link" onClick="setPage('isl/Isl.html');">ISL Overview</span><br>
        <span class="link" onClick="setPage('isl/BasicTags.html');">Basic ISL Tags</span><br>
        <span class="link" onClick="setPage('isl/FileTags.html');">File ISL Tags</span><br>
        <span class="link" onClick="setPage('isl/DataAndDisplays.html');">ISL Data and Displays</span><br>
        <span class="link" onClick="setPage('isl/ImagesAndMovies.html');">ISL Images and Movies</span><br>
        <span class="link" onClick="setPage('isl/Output.html');">Writing Text Files</span><br>
        <span class="link" onClick="setPage('isl/JythonISL.html');">Scripting with Jython</span><br>
        <span class="link" onClick="setPage('isl/Summary.html');">Tag Index</span><br>
      </div>

      <span class="link" onClick="setPage('collab/Sharing.html');">Sharing</span><br>

      <a class="toggle" onClick="toggleChildren(this,'miscellaneous_2');">+</a>
      <span class="link" onClick="setPage('page_dataanalysis.html');">Data Analysis</span><br>

      <div class="indented" id="miscellaneous_2">
        <span class="link" onClick="setPage('tools/Formulas.html');">Formulas</span><br>
        <span class="link" onClick="setPage('tools/Jython.html');">Jython Methods</span><br>
        <span class="link" onClick="setPage('tools/DerivedData.html');">Derived Data</span><br>
        <span class="link" onClick="setPage('tools/JythonShell.html');">Jython Shell</span><br>
        <span class="link" onClick="setPage('tools/JythonLib.html');">Jython Library</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'miscellaneous_3');">+</a>
      <span class="link" onClick="setPage('misc/SiteConfiguration.html');">Site Configuration</span><br>

      <div class="indented" id="miscellaneous_3">
        <span class="link" onClick="setPage('misc/Plugins.html');">Plugins</span><br>
        <span class="link" onClick="setPage('misc/PluginCreator.html');">Plugin Creator</span><br>
        <span class="link" onClick="setPage('misc/PluginJarFiles.html');">Plugin Jar Files</span><br>
        <span class="link" onClick="setPage('misc/ImageDefaults.html');">Configuring Image Defaults</span><br>
        <span class="link" onClick="setPage('data/GribTables.html');">Adding in new GRIB tables</span><br>
      </div>

      <a class="toggle" onClick="toggleChildren(this,'miscellaneous_4');">+</a>
      <span class="link" onClick="setPage('page_mcvspecialdataformats.html');">McIDAS-V Special Data Formats</span><br>

      <div class="indented" id="miscellaneous_4">
        <span class="link" onClick="setPage('data/TextPointData.html');">Text (ASCII) Point Data Format</span><br>
        <span class="link" onClick="setPage('data/LocationXml.html');">Location XML Files</span><br>
        <span class="link" onClick="setPage('data/ImageXml.html');">Image XML Files</span><br>
        <span class="link" onClick="setPage('data/ImageMovie.html');">Image Movie Files</span><br>
        <span class="link" onClick="setPage('misc/Xgrf.html');">XGRF Symbols</span><br>
      </div>

      <span class="link" onClick="setPage('misc/Actions.html');">Actions</span><br>
      <span class="link" onClick="setPage('misc/CommandLineArguments.html');">Command Line Arguments</span><br>
      <span class="link" onClick="setPage('misc/PerformanceTuning.html');">Performance Tuning</span><br>
      <span class="link" onClick="setPage('misc/SourceBuild.html');">Building McIDAS-V from Source</span><br>

    </div>

    <a class="toggle" onClick="toggleChildren(this,'appendix');">+</a>
    <span class="link" onClick="setPage('page_appendix.html');">Appendix</span><br>

    <div class="indented" id="appendix">
      <a class="toggle" onClick="toggleChildren(this,'appendix_1');">+</a>
      <span class="link" onClick="setPage('examples/index.html');">Examples of Display Types</span><br>

      <div class="indented" id="appendix_1">
        <span class="link" onClick="setPage('examples/PlanViews.html');">Plan Views</span><br>
        <span class="link" onClick="setPage('examples/3DSurface.html');">3D Surface</span><br>
        <span class="link" onClick="setPage('examples/Imagery.html');">Imagery</span><br>
        <span class="link" onClick="setPage('examples/Radar.html');">Radar -- Level II WSR-88D Data Displays</span><br>
        <span class="link" onClick="setPage('examples/Soundings.html');">Soundings</span><br>
        <span class="link" onClick="setPage('examples/Profiler.html');">Profiler Winds</span><br>
        <span class="link" onClick="setPage('examples/FlowDisplays.html');">Flow Displays</span><br>
        <span class="link" onClick="setPage('examples/Observations.html');">Observations</span><br>

        <a class="toggle" onClick="toggleChildren(this,'appendix_1_1');">+</a>
        <span class="link" onClick="setPage('examples/Miscellaneous.html');">Miscellaneous Display Types</span><br>

        <div class="indented" id="appendix_1_1">
          <span class="link" onClick="setPage('examples/Miscellaneous.html#globedisplay');">Globe Display</span><br>
          <span class="link" onClick="setPage('examples/Miscellaneous.html#multipanel');">Multi-Panel Display</span><br>
          <span class="link" onClick="setPage('examples/Miscellaneous.html#omnidisplay');">Omni Display</span><br>
        </div>

      </div>

    </div>

  </div>

</div>

<!-- SEARCH -->
<div class="small menu" id="div_search" style="display:none;">
  Find: <input name="input_search" onChange="doSearch(this.value);">

  <p>

  <div id="div_results">
  </div>

</div>

<!-- PDF -->
<div class="small menu" id="div_pdf" style="display:none;">
<img src="acrobat.gif" style="border:0px;"> Download PDF
</div>