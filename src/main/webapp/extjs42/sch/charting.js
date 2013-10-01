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
        '<img class="carimg" src="http://rasc.ch/bryntum/scheduler-2.2.9-trial/examples/charting/{Id}.jpeg" />',
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
    
    Ext.create("Ext.Container", {
        layout : { type : 'hbox', align : 'stretch' },
        renderTo: "somediv",
        height : 600,
        width : 1200,
        border: false,
        items : [
            scheduler = Ext.create("Sch.panel.SchedulerGrid", {
                flex : 1,
                viewConfig : { forceFit : true },

                title : 'Ext JS 4 Charting Demo',
                viewPreset : 'hourAndDay',
                startDate: start,
                endDate: end,
                eventBarTextField : 'Name',

                onEventCreated : function(ev) { ev.set('Name', 'New booking'); },

                // Setup static columns
                columns : [
                    { text : 'Car', width:170, align : 'center', dataIndex : 'Name', sortable : true, xtype : 'templatecolumn', tpl : carTpl}
                ],

                // Store holding all the resources
                resourceStore : resourceStore,
        
                // Store holding all the events
                eventStore: eventStore,

                rowHeight : 50,
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
                region : 'east',
                width: 300,
                layout: 'fit',
                border : false,
                padding : 10,
                items : {
                    xtype: 'chart',
                    animate: true,
                    store: chartStore,
                    shadow: true,
                    
                    insetPadding: 5,
                    theme: 'Base:gradients',
                    series: [{
                        type: 'pie',
                        field: 'usage',
                        donut: true,
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