package org.got5.tapestry5.jquery.highcharts.services;

import java.util.LinkedList;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class HighChartsCsvExportStack implements JavaScriptStack{
	
	public static final String STACK_ID = "highChartsCsvExportStack";
	
	private List<StylesheetLink> stylesheetStack;
	private List<Asset> javaScriptStack;
	
	
	public HighChartsCsvExportStack(
			@Symbol(SymbolConstants.PRODUCTION_MODE) final boolean productionMode, 
			final AssetSource assetSource) {
		
		super();
		
		final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {

            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        stylesheetStack = CollectionFactory.newList();
        
        String exportModulePAth= "${jquery.highcharts.core.path}/modules/export-csv.js" ;//+
        		//(productionMode? "exporting.js" : "exporting.src.js");
        
        javaScriptStack = F.flow(exportModulePAth).map(pathToAsset).toList();
	}
	
	

	public List<String> getStacks() {
		List<String> res= new LinkedList<String>();
		res.add(HighChartsJqueryStack.STACK_ID);
		return res;
	}

	public List<Asset> getJavaScriptLibraries() {
		return this.javaScriptStack;
	}

	public List<StylesheetLink> getStylesheets() {
		return this.stylesheetStack;
	}

	public String getInitialization()
    {
        return null;
    }

}
