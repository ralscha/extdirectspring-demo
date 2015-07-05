Ext.require([ 'Sch.panel.SchedulerGrid', 'Sch.plugin.CurrentTimeLine' ]);

Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

    var programTemplate = new Ext.XTemplate(
        '<span class="startTime">{[fm.date(values.StartDate, "G:i")]}</span>' +
            '<span class="programName">{text}</span>' +
            '<span class="duration">{[((values.duration / 60) + " min")]}</span>'
    );

	Ext.define('Station', {
		extend: 'Sch.model.Resource',
		idProperty: 'Id',
		fields: [ 'Id', 'Name' ],
		proxy: {
			type: 'direct',
			directFn: bbcService.fetchStations
		}
	});

	var stationStore = new Sch.data.ResourceStore({
		model: 'Station',
		autoLoad: true
	});

	Ext.define('Program', {
		extend: 'Sch.model.Event',
		idProperty: 'Id',
		fields: [ {
			name: 'Id'
		}, {
			name: 'ResourceId'
		}, {
			name: 'StartDate',
			type: 'date',
			dateFormat: 'c'
		}, {
			name: 'EndDate',
			type: 'date',
			dateFormat: 'c'
		}, {
			name: 'text'
		}, {
			name: 'duration'
		}, {
			name: 'synopsis'
		} ],
		proxy: {
			type: 'direct',
			directFn: bbcService.fetchSchedule
		}
	});

	var programStore = new Sch.data.EventStore({
		model: 'Program',
		autoLoad: true
	});

	var start = new Date();
	Ext.Date.clearTime(start);
	var end = Sch.util.Date.add(start, Sch.util.Date.DAY, 1);

	Sch.preset.Manager.registerPreset("hour", {
		displayDateFormat: 'G:i',
		shiftIncrement: 1,
		shiftUnit: "DAY",
		timeColumnWidth: 150,
		timeResolution: {
			unit: "MINUTE",
			increment: 5
		},
		headerConfig: {
			middle: {
				unit: "HOUR",
				dateFormat: 'G:i',
				align: 'left'
			}
		}
	});

	var g = new Sch.panel.SchedulerGrid({
		readOnly: true,
		height: 700,
		width: 1200,
		renderTo: 'grid-bbc',
		resourceStore: stationStore,
		eventStore: programStore,
		eventBodyTemplate: programTemplate,
		rowHeight: 70,
		enableHdMenu: false,
		tooltipTpl: new Ext.XTemplate('<span class="radiotip">{[fm.date(values.StartDate, "G:i")]}</span> {synopsis}'),
		startDate: start,
		endDate: end,
		border: false,
		rowLines: false,
		viewPreset: 'hour',
		columns: [ {
			xtype: 'templatecolumn',
			header: 'Station',
			align: 'center',
			width: 150,
			dataIndex: 'Name',
			tpl: '<img class="station-img" src="//demo.rasc.ch/resources/bryntum/scheduler-3.0.4-trial/examples/bbc/images/{Id}.png" />'
		} ],
		plugins: new Sch.plugin.CurrentTimeLine(),
		viewConfig: {
			rowLines: false,
			barMargin: 5
		},
		columnLines: false
	});

	programStore.on('load', function() {
		g.scrollToDate(Ext.Date.add(new Date(), Sch.util.Date.HOUR, -1), {
			duration: 2000
		});
	});

});
