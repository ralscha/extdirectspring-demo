Ext.define('carstore.controller.CarListingsViewController', {
	extend: 'Deft.mvc.ViewController',
	inject: [ 'carChart', 'carData' ],

	control: {
		carGrid: {
			selectionchange: 'onCarGridSelectionChange',
			viewready: 'onViewReady'
		},
		carDetail: true,
		carChartPanel: true,
		wikipediaIFrame: true
	},

	config: {
		carChart: null,
		carData: null
	},

	onViewReady: function() {
		this.getCarData().load({
			scope: this,
			callback: function() {
				this.getCarGrid().getSelectionModel().select(0);
			}
		});
	},

	onCarGridSelectionChange: function(selectionModel, selections) {
		var data = selections[0].getData();
		this.getCarDetail().update(data);
		this.getCarChart().loadData(data.quality);
		this.getWikipediaIFrame().load(data.wiki);
	}

});