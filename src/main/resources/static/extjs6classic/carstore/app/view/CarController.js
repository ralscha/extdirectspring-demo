Ext.define('Carstore.view.CarController', {
	extend: 'Ext.app.ViewController',

	init: function(view) {
		var carStore = this.getStore('carStore');
		carStore.load({
			scope: this,
			callback: function() {
				this.lookupReference('carGrid').setSelection(carStore.first());
			}
		});		
	},
	
	onGridSelectionChange: function(grid, selected) {
		this.lookupReference('carDetail').update(selected[0]);		
		this.getStore('chartStore').loadData(selected[0].data.quality);		
		this.lookupReference('wikiFrame').down('uxiframe').load(selected[0].data.wiki);
	}

});