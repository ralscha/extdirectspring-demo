Ext.Loader.setConfig({ enabled : true, disableCaching : true });

Ext.require([
    'Sch.panel.SchedulerGrid'
]);

Ext.onReady(function(){
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
	
    Ext.define('Resource', {
        extend : 'Sch.model.Resource',
        idProperty : 'Id',
        fields: ['Id', 'Name', 'Seats'],
         proxy: {
     		type: 'direct',
     		directFn: schCarService.readCars
     	}
    });
    
    Ext.define('Event', {
        extend : 'Sch.model.Event',
        idProperty : 'Id',
        fields: ['Id', 'Name', 'ResourceId', 
                 { name: 'StartDate', type: 'date', dateFormat: 'c' }, 
                 { name: 'EndDate', type: 'date', dateFormat: 'c' }
                ], 
        proxy: {
    		type: 'direct',
    	    api: {
    	        read: schCarService.readEvents,
    	        create: schCarService.createEvent,
    	        update: schCarService.updateEvent,
    	        destroy: schCarService.destroyEvent
    	    }	    		
        }
    });    

    var carTpl = Ext.create('Ext.XTemplate', 
        '<img class="carimg" src="http://rasc.ch/Bryntum-2.x-45d-trial/scheduler-2.1.6-trial/examples/charting/{Id}.jpeg" />',
        '<dl class="cardescr">',
            '<dt>{Name}</dt>',
            '<dd>{Seats} seats</dd>',
        '</dl>'
    );

    var resourceStore = Ext.create('Sch.data.ResourceStore', {
        sorters:{
            property: 'Name', 
            direction: "ASC"
        },
        model : 'Resource',
        autoLoad : true
    });
        
   var eventStore = Ext.create('Sch.data.EventStore', {
       model : 'Event',
       autoLoad : true,
       autoSync: true
    });

    var scheduler,
        chartStore = new Ext.data.JsonStore({
            fields : ['name', 'usage']
        });

    eventStore.on({
        'update' : refreshChart,
        'add' : refreshChart,
        'load' : refreshChart
    });

    var start = new Date();
    Ext.Date.clearTime(start);
    var end = Sch.util.Date.add(start, Sch.util.Date.DAY, 1);
    
    Ext.create("Ext.Panel", {
        layout : 'border',
        renderTo: "somediv",
        height : 600,
        width : 1200,
        items : [
            scheduler = Ext.create("Sch.panel.SchedulerGrid", {
                region : 'center',

                title : 'Ext JS 4 Charting Demo',
                viewPreset : 'hourAndDay',
                eventBarTextField : 'Name',

                startDate: start,
                endDate: end,
                
                onEventCreated : function(ev) { ev.set('Name', 'New booking'); },
                columns : [
                    { text : 'Car', width:230, align : 'center', dataIndex : 'Name', sortable : true, xtype : 'templatecolumn', tpl : carTpl}
                ],
                resourceStore : resourceStore,
                eventStore: eventStore,
                rowHeight : 70,
                snapToIncrement : false,
                barMargin : 4,
                tbar : [
                    {
                        text : 'Previous day',
                        iconCls : 'icon-previous',
                        handler : function() {
                            scheduler.shiftPrevious();
                        }
                    },
                    {
                        text : 'Next day',
                        iconCls : 'icon-next',
                        handler : function() {
                            scheduler.shiftNext();
                        }
                    },
                    {
                        text            : 'Horizontal view',
                        pressed         : true,
                        
                        enableToggle    : true,
                        toggleGroup     : 'orientation',
                        
                        iconCls         : 'icon-horizontal',
                        
                        scope           : this,
                        handler         : function() {
                            scheduler.setOrientation('horizontal');
                        }
                    },
                    {
                        text            :  'Vertical view',
                        
                        enableToggle    : true,
                        toggleGroup     : 'orientation',
                        
                        iconCls         : 'icon-vertical',
                        
                        scope           : this,
                        handler         : function() {
                            scheduler.setOrientation('vertical');
                        }
                    }
                ]
            }),
            Ext.create('widget.panel', {
                title : 'Allocated per car (% of total)',
                region : 'east',
                width: 400,
                layout: 'fit',
                items : {
                    xtype: 'chart',
                    animate: true,
                    store: chartStore,
                    shadow: true,
                    
                    insetPadding: 20,
                    theme: 'Base:gradients',
                    series: [{
                        type: 'pie',
                        field: 'usage',
                        donut: true,
                        highlight: {
                          segment: {
                            margin: 20
                          }
                        },
                        label: {
                            field: 'name',
                            display: 'rotate',
                            contrast: true,
                            font: '18px Arial'
                        }
                    }]
                }
        })
        ]
    });
    
    scheduler.on('viewchange', refreshChart);

    refreshChart();

    function refreshChart() {
        var data = [],
            ta = scheduler.getTimeAxis(),
            start = ta.getStart(),
            end = ta.getEnd();
        
        var totalAllocatedTime = 0;
        
        eventStore.queryBy(function(eRec) {
            totalAllocatedTime += eRec.getEndDate() - eRec.getStartDate(); 
        });

        resourceStore.each(function(r) {
            var carAllocatedTime = 0;
            
            eventStore.queryBy(function(eRec) {
                if (eRec.getResourceId() === r.get('Id') && Sch.util.Date.intersectSpans(start, end, eRec.getStartDate(), eRec.getEndDate())) {
                   carAllocatedTime += eRec.getEndDate() - eRec.getStartDate(); 
                }
            });
            
            data.push({
                name : r.get('Name'),
                usage : Math.round(100 * carAllocatedTime / totalAllocatedTime)
            });
        });
        chartStore.loadData(data);
    }
});