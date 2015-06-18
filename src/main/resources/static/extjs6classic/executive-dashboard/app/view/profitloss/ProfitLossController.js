Ext.define('ExecDashboard.view.profitloss.ProfitLossController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.profitloss',

	
	onBeforeActivate: function() {
		execDashboardService.readMetaProfitloss(function(result) {
			this.metaDataLoad(result);
		}, this);
	},
	
	metaDataLoad: function(metaProfitLoss) {

		var me = this, menus = {
			quarter: {
				items: [],
				listeners: {
					click: me.onQuarterItemClick,
					scope: me
				}
			},
			region: {
				items: [],
				listeners: {
					click: me.onRegionItemClick,
					scope: me
				}
			}
		}, columns = [ me.getView().regionColumn ];

		metaProfitLoss.regions.forEach(function(region) {
			menus.region.items.push(Ext.apply({
				text: region,
				value: region,
				listeners: menus.region.listeners
			}, me.getView().menuItemDefaults));
		});

		metaProfitLoss.quarters.forEach(function(quarter) {
			var text = quarter.substring(0, 2).toUpperCase() + ' ' + quarter.substring(3);
			menus.quarter.items.push(Ext.apply({
				text: text,
				value: quarter,
				listeners: menus.quarter.listeners
			}, me.getView().menuItemDefaults));

			columns.push(Ext.apply({
				text: text,
				dataIndex: quarter
			}, me.getView().quarterColumnDefaults));
		});

		menus.region.items.sort(function(lhs, rhs) {
			return (lhs.text < rhs.text) ? -1 : ((rhs.text < lhs.text) ? 1 : 0);
		});

		// We want to tinker with the UI in batch so we don't trigger multiple layouts
		Ext.batchLayouts(function() {
			me.getReferences().quartersButton.menu.removeAll();
			me.getReferences().regionsButton.menu.removeAll();
			
			me.getReferences().quartersButton.menu.add(menus.quarter.items);
			me.getReferences().regionsButton.menu.add(menus.region.items);
			me.getView().setColumns(columns);
			me.getView().store.load();
		});
	},

	onQuarterItemClick: function(menuItem) {
		var column = this.getView().getColumnManager().getHeaderByDataIndex(
				menuItem.value);
		column.setVisible(menuItem.checked);
	},

	onRegionItemClick: function() {
		var view = this.getView(), filter = {
			// The id ensures that this filter will be replaced by subsequent calls
			// to this method (while leaving others in place).
			id: 'regionFilter',
			property: 'region',
			operator: 'in',
			value: []
		}, regionMenu = this.lookupReference('regionsButton').menu;

		regionMenu.items.each(function(item) {
			if (item.checked) {
				filter.value.push(item.value);
			}
		});

		if (filter.value.length === regionMenu.items.length) {
			// No need for a filter that includes everything, so remove it (in case it
			// was there - harmless if it wasn't)
			view.store.getFilters().removeByKey(filter.id);
		}
		else {
			view.store.getFilters().add(filter);
		}
	},

	// Fix an issue when using touch scrolling and hiding columns, occasionally
	// there is an issue wher the total scroll size is not updated.
	onViewRefresh: function(view) {
		if (view.ownerGrid.normalGrid === view.ownerCt) {
			var scrollManager = view.scrollManager, scroller;

			if (scrollManager) {
				scroller = scrollManager.scroller;
				scroller.setSize('auto');
				scroller.refresh();
			}
		}
	}
});
