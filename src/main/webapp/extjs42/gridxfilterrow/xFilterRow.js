/**
 * Ext.ux.grid.xFilterRow is a software developed by zonereseau.com, 
 * Copyright (C) 2007-2012 zonereseau.com.
 *  
 * Ext.ux.grid.xFilterRow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * Ext.ux.grid.xFilterRow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Ext.ux.grid.xFilterRow.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 * 
 */

Ext.define('Ext.ux.grid.xFilterRow', {
    extend: 'Ext.AbstractPlugin',
    mixins: {
        observable: 'Ext.util.Observable'
    },    
	
	remoteFilter:true,	
	
	init: function(grid) {
		var me = this;

		Ext.apply(grid, {
            xFilterRow: me,
            getXFilterRow: function() {
                return this.xFilterRow;
            }
        });

		me.grid  = grid;
		me.grids = [];
		
		if(me.grid.lockedGrid) {
			me.grids = [me.grid.lockedGrid, me.grid.normalGrid];
		} else {
			me.grids = [me.grid];
		}
		
		// Setup events and apply the template 
		for(var i=0; i<me.grids.length; i++) {
			
			me.grids[i].on({
				columnresize: me.resizeXFilterHD,
				columnhide: me.resizeXFilterHD,
				columnshow: me.resizeXFilterHD,
				columnmove: me.moveXFilterHD,
				beforedestroy: me.destroyXFilterRow,
				reconfigure: me.reconfigureXFilterRow,
				scope: me
	        });				

			// Override Grid horizontal scroll
			me.grids[i].syncHorizontalScroll = function(left, setBody) {
				var me = grid,
		            scrollTarget;

	            // Fix locking grid issue
	            if(me.normalGrid) {
	            	me = grid = me.normalGrid;
	            }
				
		        setBody = setBody === true;			        
		        
		        if (me.rendered && (setBody || left !== me.scrollLeftPos)) {
		            
		            if (setBody) {   
		                scrollTarget = me.getScrollTarget();
		                scrollTarget.el.dom.scrollLeft = left;
		            }
		            
		            me.headerCt.el.dom.scrollLeft = left;
		            me.scrollLeftPos = left;
		            
		            // Synchronize header with the filter row
		            var width = me.headerCt.el.dom.firstChild.style.width;
					me.dockedXFilterRow.el.dom.firstChild.style.width = width;
					me.dockedXFilterRow.el.dom.scrollLeft = left;
		        }
		    };
		}
		
		me.setup();
	},
	
	
	setup: function() {
		
		var me = this;
		
		// renders the filter's bar
        if (me.grid.rendered) {
            me.renderXFilterRow();
        } else {
            me.grid.on('afterrender', me.renderXFilterRow, me, { single: true });
        }
		
	},
	
	destroyXFilterRow:function() {
		var me = this;
		
		for(var i=0; i<me.grids.length; i++) {
			Ext.getCmp(me.grids[i].id+'docked-filter').remove();
			delete me.grids[i].dockedXFilterRow;
		}
		
	},
	
	reconfigureXFilterRow:function() {
		var me = this;
		
		me.destroyXFilterRow();
		me.setup();
		
	},
	
	moveXFilterHD: function(headerCt, column, fromIdx, toIdx, eOpts) {
		var me = this;

		// When we move a column from left to right we
		// need to change the target index
		if(fromIdx < toIdx) { toIdx--; }
		
		column.xfilterField.ownerCt.move(fromIdx, toIdx);
	},
		
	resizeXFilterHD: function (headerCt, column, newColumnWidth) {
		var me = this;
		
		// Ajust width
		if(column.xfilterField) {
			column.xfilterField.setWidth(newColumnWidth);
			
			// hidden or not ?
			if(column.hidden) {
				column.xfilterField.hide();
			} else {
				column.xfilterField.show();
			}
		}
		
	},
	
	renderXFilterRow:function() {
				
		// Grids to renders
		var me = this;
		
		for(var i=0; i<me.grids.length; i++) {
			grid = me.grids[i];		
				
			var searchItems = [];		
			this.eachColumn(grid.columns, function(column) {
								
				// Verify for grouped columns
				var columns = [];				
				if(column.items.items && column.items.items.length > 0) {
					for(var x=0; x<column.items.items.length; x++) {
						columns.push(column.items.items[x]);
					}
				} else {
					columns.push(column);	
				}
				
				for(var x=0; x<columns.length; x++) {
					
					var col = columns[x];
					
				
					var filterDivId = me.getFilterDivId(col.id);
					
					var width = (col.el ? col.getWidth() : col.width);
					if (!col.xfilterField) {
										
						if(col.noxfilter || col.isCheckerHd != undefined || col.xtype == 'rownumberer') {
							col.xfilter = { };
						} else if(!col.xfilter){
							col.xfilter = { };
							col.xfilter.xtype = 'textfield';
						}
						
						col.xfilter = Ext.apply({
							grid:grid,
							id:filterDivId,
							hidden:col.hidden,
							xtype:'component',
							baseCls: "xfilter-row",
							width:width,
							enableKeyEvents:true,
							hideLabel:true					
						}, col.xfilter);
						
						col.xfilterField = Ext.ComponentManager.create(col.xfilter);
						
					} else {
						if(col.hidden != col.xfilterField.hidden) {
							col.xfilterField.setVisible(!col.hidden);
						}
					}
					
					if(col.xfilterField.xtype == 'combo') {
						col.xfilterField.on("select", me.onComboSelect, me);
					} else if(col.xfilterField.xtype == 'datefield') {
						col.xfilterField.on("change", me.onDateFieldChange, me);
					}
					
					col.xfilterField.on("keydown", me.onFieldKeyDown, me);
					col.xfilterField.on("focus", me.onFieldFocus, me);
					col.xfilterField.on("blur", me.onFieldBlur, me);
					col.xfilterField.setWidth(width);
					
					searchItems.push(col.xfilterField);
				}
				
			});			
			
			if(searchItems.length > 0) {
				grid.addDocked(grid.dockedXFilterRow = Ext.create('Ext.container.Container', {
					id:grid.id+'docked-filter',
					weight: 100,
					dock: 'top',
					border: false,
					baseCls: Ext.baseCSSPrefix + 'grid-header-ct',
					items:searchItems,
					layout:{
		                type: 'hbox'
		            }
				}));			
			}			
		}
	},
	
	
	onComboSelect: function(field, value, option) {
		var me = this;
		if(!me.onChangeTask) {
			me.onChangeTask = new Ext.util.DelayedTask(function(){
	    		me.storeSearch();	
			}, me);
		}
		
		me.onChangeTask.delay(1000);
	},
	
	onDateFieldChange: function(field, newValue, oldValue) {
		var me = this;
		
		if(!me.onChangeTask) {
			me.onChangeTask = new Ext.util.DelayedTask(function(){
	    		me.storeSearch();	
			}, me);
		}
		
		me.onChangeTask.delay(1000);
				
	},
	
	onFieldKeyDown: function(field, e) {
		var me = this;
		if(e.getKey() == e.ENTER) {
			me.storeSearch();			
		}
	},
	
	onFieldFocus:function(field) {
		var me 		= this;
		var pos 	= field.getPosition(true);
		var width	= field.getWidth();
		var left 	= pos[0];
		var grid 	= field.grid;
	
		// TAB fix
		var scrollLeft 	= grid.headerCt.el.dom.scrollLeft;
		var clientWidth = grid.headerCt.el.dom.clientWidth;

		if(left < scrollLeft || left+width > clientWidth) {
			grid.syncHorizontalScroll(left, true);
		}
	},
	
	onFieldBlur:function(field) {		
		var me = this;
		
		// Fix for the last field blur
		var grid = field.grid;
		var columns = grid.headerCt.getVisibleGridColumns();

		if(columns[columns.length-1].xfilterField == field) {
			me.onFieldFocus(columns[0].xfilterField);
		}
	},
	
	getSearchValues: function() {
		var me = this;		
		var values = {};
		
		for(var i=0; i<me.grids.length; i++) {
			grid = me.grids[i];	
			
			me.eachColumn(grid.columns, function(column) {
				
				// Verify for grouped columns
				var columns = [];				
				if(column.items.items && column.items.items.length > 0) {
					for(var x=0; x<column.items.items.length; x++) {
						columns.push(column.items.items[x]);
					}
				} else {
					columns.push(column);	
				}
				
				for(var x=0; x<columns.length; x++) {
					
					var col = columns[x];
					
					if(col.xfilterField && col.xfilterField.xtype != 'component') {
						var value = col.xfilterField.getValue();
						if(value && value != '') {
							values[col.dataIndex] = col.xfilterField.getValue();
						}
					}
				}
				
			});				
		}
		
		return values;
	},	
	
	storeSearch: function() {		
		var me = this;
		
		if(me.remoteFilter) {
			if(!me.grid.store.proxy.extraParams) {
				me.grid.store.proxy.extraParams = {};
			}		
			me.grid.store.proxy.extraParams.search = me.getSearchValues();
			me.grid.store.currentPage = 1;
			me.grid.store.load();
		} else {
			
			var values = me.getSearchValues();
						
			if (me.isEmptyObject(values)) {
				me.grid.store.clearFilter();
			} else {
				var saveRemoteFilterValue = me.grid.store.remoteFilter;
				me.grid.store.remoteFilter = false;
				me.grid.store.clearFilter();
				me.grid.store.remoteFilter = saveRemoteFilterValue;
			}
			
			for(key in values) {
				me.grid.store.filter(key, values[key]);
			}			
		}
	},
	
	isEmptyObject: function(obj) {
	    for(var prop in obj) {
	        if(obj.hasOwnProperty(prop))
	            return false;
	    }

	    return true;
	},
	
	clearSearchValues:function() {		
		var me = this;
		
		for(var i=0; i<me.grids.length; i++) {
			grid = me.grids[i];	
			me.eachColumn(grid.columns, function(col) {
				if(col.xfilterField && col.xfilterField.xtype != 'component') {
					col.xfilterField.setValue('');
				}
			});
		}
	},

	clearSearch:function() {	
		var me = this;
		me.clearSearchValues();
		me.storeSearch();		
	},
				
	// Returns HTML ID of element containing filter div
	getFilterDivId: function(columnId) {
		var me = this;
		return me.grid.id + '-xfilter-' + columnId;
	},
		
	// Iterates over each column in column config array
	eachColumn: function(columns, func) {
		var me = this;
		Ext.each(columns, func, me);
	}
});
