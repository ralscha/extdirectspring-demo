Ext.onReady(function () {
	
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
	
    var store = Ext.create('Ext.data.JsonStore', {
        fields: [  { name: 'date', type: 'date', dateFormat:'Y-m-d' }, 'visits', 'views', 'veins']
    });
    
    chartService.getFirst10SiteInfos(function(result) {
    	store.loadData(result);  
    });

    setInterval(function() {    	
    	chartService.getNextSiteInfo(function(result) {    		
        	store.removeAt(0);
        	store.add(result);  		
    	});

    }, 1000);

    Ext.create('Ext.Window', {    	
        width: 800,
        height: 600,
        minHeight: 400,
        minWidth: 550,
        hidden: false,
        maximizable: true,
        title: 'Live Animated Chart',
        renderTo: Ext.getBody(),
        layout: 'fit',
        items: [{
            xtype: 'cartesian',   
            id: 'chartCmp',
            store: store,
            animation: {
            	easing: 'backOut',
            	duration: 700
            },            
            axes: [{
                type: 'numeric',
                fields: ['views', 'visits', 'veins'],
                title: 'Number of Hits',
                position: 'left',
                grid: true,
                minimum: 0,
                maximum: 100
            }, {
            	type: 'category',
                position: 'bottom',
                fields: 'date',
                title: 'Day',
                grid: true,
                renderer: function(v) {
                	return Ext.Date.format(v, 'M d');
                }
            }],
            series: [{
                type: 'line',
                xField: 'date',
                yField: 'visits',
                style: {
                    lineWidth: 4
                },
                marker: {
                    radius: 4
                }
            }, {
                type: 'line',
                xField: 'date',
                yField: 'views',
                style: {
                    lineWidth: 4
                },
                marker: {
                    radius: 4
                }           
            }, {
                type: 'line',
                xField: 'date',
                yField: 'veins',
                style: {
                    lineWidth: 2
                },
                marker: {
                    radius: 4
                }       	
            }]
        }]

    });
});
