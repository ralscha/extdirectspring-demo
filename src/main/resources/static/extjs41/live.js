Ext.require('Ext.chart.*');

Ext.onReady(function () {
	
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
	
	var data = [];	
    var chart,timeAxis;

    var store = Ext.create('Ext.data.JsonStore', {
        fields: ['date', 'visits', 'views', 'veins']
    });
    
    chartService.getFirst10SiteInfos(function(result) {
    	data = result;
    	store.loadData(data);   
    });

    setInterval(function() {
    	
    	chartService.getNextSiteInfo(function(result) {
    		data = data.slice();
    		data.push(result);
            var toDate = timeAxis.toDate,
                lastDate = Ext.Date.parse(result.date, "Y-m-d");
                markerIndex = chart.markerIndex || 0;
            if (+toDate < +lastDate) {
                markerIndex = 1;
                timeAxis.toDate = lastDate;
                timeAxis.fromDate = Ext.Date.add(Ext.Date.clone(timeAxis.fromDate), Ext.Date.DAY, 1);
                chart.markerIndex = markerIndex;
            }
            store.loadData(data);    		
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
            xtype: 'chart',
            style: 'background:#fff',
            id: 'chartCmp',
            store: store,
            shadow: false,
            animate: true,
            axes: [{
                type: 'Numeric',
                grid: true,
                minimum: 0,
                maximum: 100,
                position: 'left',
                fields: ['views', 'visits', 'veins'],
                title: 'Number of Hits',
                grid: {
                    odd: {
                        fill: '#dedede',
                        stroke: '#ddd',
                        'stroke-width': 0.5
                    }
                }
            }, {
                type: 'Time',
                position: 'bottom',
                fields: 'date',
                title: 'Day',
                dateFormat: 'M d',
                constrain: true,
                fromDate: new Date(2011, 0, 1),
                toDate: new Date(2011, 0, 11),
                grid: true
            }],
            series: [{
                type: 'line',
                smooth: false,
                axis: ['left', 'bottom'],
                xField: 'date',
                yField: 'visits',
                label: {
                    display: 'none',
                    field: 'visits',
                    renderer: function(v) { return v >> 0; },
                    'text-anchor': 'middle'
                },
                markerConfig: {
                    radius: 5,
                    size: 5
                }
            },{
                type: 'line',
                axis: ['left', 'bottom'],
                smooth: false,
                xField: 'date',
                yField: 'views',
                label: {
                    display: 'none',
                    field: 'visits',
                    renderer: function(v) { return v >> 0; },
                    'text-anchor': 'middle'
                },
                markerConfig: {
                    radius: 5,
                    size: 5
                }
            },{
                type: 'line',
                axis: ['left', 'bottom'],
                smooth: false,
                xField: 'date',
                yField: 'veins',
                label: {
                    display: 'none',
                    field: 'visits',
                    renderer: function(v) { return v >> 0; },
                    'text-anchor': 'middle'
                },
                markerConfig: {
                    radius: 5,
                    size: 5
                }
            }]
        }]
    });
    chart = Ext.getCmp('chartCmp');
    timeAxis = chart.axes.get(1);
});
