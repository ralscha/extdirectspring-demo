/*
    Ext.ux.grid.mzPivotGrid component for Extjs 4.1.x
    Copyright (C) 2012  Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
* @private
* @class Ext.ux.view.mzPivotChunker
* @author Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @docauthor Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @license Dual license available: [GPLv3][1] & commercial. Please contact me for commercial licenses.
* 
* @version 1.0
* 
* [1]: http://www.gnu.org/copyleft/gpl.html
* 
* This class handles the pivot grid template. The default grid template chunker didn't allow row span.
*/
Ext.define('Ext.ux.view.mzPivotChunker', {
    singleton: true,
    requires: ['Ext.XTemplate'],
    metaTableTpl: [
        '{%if (this.openTableWrap)out.push(this.openTableWrap())%}',
        '<table class="' + Ext.baseCSSPrefix + 'grid-table ' + Ext.baseCSSPrefix + 'grid-table-resizer" border="0" cellspacing="0" cellpadding="0" {[this.embedFullWidth(values)]}>',
            '<tbody>',
            '<tr class="' + Ext.baseCSSPrefix + 'grid-header-row">',
            '<tpl for="columns">',
                '<th class="' + Ext.baseCSSPrefix + 'grid-col-resizer-{id}" style="width: {width}px; height: 0px;"></th>',
            '</tpl>',
            '</tr>',
            '{[this.openRows()]}',
                '{row}',
                '<tpl for="features">',
                    '{[this.embedFeature(values, parent, xindex, xcount)]}',
                '</tpl>',
            '{[this.closeRows()]}',
            '</tbody>',
        '</table>',
        '{%if (this.closeTableWrap)out.push(this.closeTableWrap())%}'
    ],

    constructor: function() {
        Ext.XTemplate.prototype.recurse = function(values, reference) {
            return this.apply(reference ? values[reference] : values);
        };
    },

    embedFeature: function(values, parent, x, xcount) {
        var tpl = '';
        if (!values.disabled) {
            tpl = values.getFeatureTpl(values, parent, x, xcount);
        }
        return tpl;
    },

    embedFullWidth: function(values) {
        var result = 'style="width:{fullWidth}px;';

        
        
        if (!values.rowCount) {
            result += 'height:1px;';
        }
        return result + '"';
    },

    openRows: function() {
        return '<tpl for="rows">';
    },

    closeRows: function() {
        return '</tpl>';
    },

    metaRowTpl: [
        '<tr class="' + Ext.baseCSSPrefix + 'grid-row {[this.embedRowCls()]}" {[this.embedRowAttr()]}>',
            '<tpl for="columns">',
                '{[this.openCellCheckHidden(values.id)]}',
                    '<td class="{cls} ' + Ext.baseCSSPrefix + 'grid-cell ' + Ext.baseCSSPrefix + 'grid-cell-{columnId} {{id}-modified} {{id}-tdCls} {[this.firstOrLastCls(xindex, xcount)]}" {{id}-tdAttr}>',
                        '{[this.openGroup(values.id)]}',
                            '<div class="' + Ext.baseCSSPrefix + 'grid-row-expander" style="float:left">&nbsp;</div>',
                        '{[this.closeGroup()]}',
                        '<div {unselectableAttr} class="' + Ext.baseCSSPrefix + 'grid-cell-inner {unselectableCls}" style="text-align: {align}; {{id}-style};">{{id}}</div>',
                    '</td>',
                '{[this.closeCellCheckHidden()]}',
            '</tpl>',
        '</tr>'
    ],

    openCellCheckHidden: function(id){
        return '<tpl if="this.isCellVisible(values, \'' + id + '-tdAttr\')">';
    },
    
    closeCellCheckHidden: function(){
        return '</tpl>';
    },
    
    openGroup: function(id){
        return '<tpl if="this.isGroup(values, \'' + id + '-tdCls\')">';
    },
    
    closeGroup: function(){
        return '</tpl>';
    },
    
    isGroup: function(values, id){
        return Ext.Array.contains(Ext.String.splitWords(values[id]), 'group');
    },
    
    isCellVisible: function(values, id){
        return values[id] != 'hidden';
    },

    firstOrLastCls: function(xindex, xcount) {
        if (xindex === 1) {
            return Ext.view.Table.prototype.firstCls;
        } else if (xindex === xcount) {
            return Ext.view.Table.prototype.lastCls;
        }
    },
    
    embedRowCls: function() {
        return '{rowCls}';
    },
    
    embedRowAttr: function() {
        return '{rowAttr}';
    },
    
    openTableWrap: undefined,
    
    closeTableWrap: undefined,

    getTableTpl: function(cfg, textOnly) {
        var tpl,
            tableTplMemberFns = {
                openRows: this.openRows,
                closeRows: this.closeRows,
                embedFeature: this.embedFeature,
                embedFullWidth: this.embedFullWidth,
                openTableWrap: this.openTableWrap,
                closeTableWrap: this.closeTableWrap
            },
            tplMemberFns = {
                isCellVisible:  this.isCellVisible,
                isGroup:        this.isGroup
            },
            features = cfg.features || [],
            ln = features.length,
            i  = 0,
            memberFns = {
                embedRowCls: this.embedRowCls,
                embedRowAttr: this.embedRowAttr,
                firstOrLastCls: this.firstOrLastCls,
                unselectableAttr: cfg.enableTextSelection ? '' : 'unselectable="on"',
                unselectableCls: cfg.enableTextSelection ? '' : Ext.baseCSSPrefix + 'unselectable',
                openCellCheckHidden: this.openCellCheckHidden,
                closeCellCheckHidden: this.closeCellCheckHidden,
                openGroup: this.openGroup,
                closeGroup: this.closeGroup
            },
            
            metaRowTpl = Array.prototype.slice.call(this.metaRowTpl, 0),
            metaTableTpl;
            
        for (; i < ln; i++) {
            if (!features[i].disabled) {
                features[i].mutateMetaRowTpl(metaRowTpl);
                Ext.apply(memberFns, features[i].getMetaRowTplFragments());
                Ext.apply(tplMemberFns, features[i].getFragmentTpl());
                Ext.apply(tableTplMemberFns, features[i].getTableFragments());
            }
        }
        
        metaRowTpl = new Ext.XTemplate(metaRowTpl.join(''), memberFns);
        cfg.row = metaRowTpl.applyTemplate(cfg);
        
        metaTableTpl = new Ext.XTemplate(this.metaTableTpl.join(''), tableTplMemberFns);
        
        tpl = metaTableTpl.applyTemplate(cfg);
        
        
        if (!textOnly) {
            tpl = new Ext.XTemplate(tpl, tplMemberFns);
        }
        return tpl;
        
    }
});


/**
* @private
* @class Ext.ux.grid.column.mzPivotColumn
* @extends Ext.grid.column.Column
* @author Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @docauthor Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @license Dual license available: [GPLv3][1] & commercial. Please contact me for commercial licenses.
* 
* @version 1.2
*
* [1]: http://www.gnu.org/copyleft/gpl.html
* 
* This class is used for left axis grid columns to be able to render the row span.
* 
* Changelog:
* 
* 21.08.2012 - v1.1 - Added posibility to hide table columns and mark table rows as grouping rows.
* 
* 18.09.2012 - v1.2 - Added check for "enableGrouping" property of the pivot grid.
* 
*/
Ext.define('Ext.ux.grid.column.mzPivotColumn', {
    extend: 'Ext.grid.column.Column',
    alias: 'widget.mzpivotcolumn',

    text:           "&#160",
    width:          23,
    sortable:       false,
    draggable:      false,
    resizable:      true,
    hideable:       false,
    menuDisabled:   true,
    locked:         false,
    dataIndex:      '',
    cls:            Ext.baseCSSPrefix + 'row-numberer',
    rowspan:        undefined,
    align:          'left',

    constructor : function(config){
        this.width = this.width;
        this.locked = true;

        this.callParent(arguments);
        this.renderer = Ext.Function.bind(this.renderer, this);
    },

    renderer: function(value, metaData, record, rowIdx, colIdx, store) {
        var me = this;
        
        metaData.tdCls = Ext.baseCSSPrefix + 'grid-cell-special';
        metaData.tdAttr = '';

        if(me.pivotGrid.enableGrouping && record.get('rowGroup') > 1 && this.dataIndex == record.get('rowGroupField')){
            metaData.tdCls += ' group';
        }

        if(record.get('rowspan' + this.dataIndex) == -1){
            metaData.tdAttr = 'hidden';
        }
        if(record.get('colspan' + this.dataIndex) == -1){
            metaData.tdAttr = 'hidden';
        }
            
        if(record.get('rowspan' + this.dataIndex) > 0){
            metaData.tdAttr += ' rowspan="' + record.get('rowspan' + this.dataIndex) + '" valign="top"';
        }
        
        if(record.get('colspan' + this.dataIndex) > 0){
            metaData.tdAttr += ' colspan="' + record.get('colspan' + this.dataIndex) + '" valign="top"';
        }
        
        if(record.get('rowGroup')){
            return this.dataIndex == record.get('rowGroupField') ? value : '';
        }else{
            return value;
        }
    }
    
});


/**
* @class Ext.ux.grid.mzPivotGrid
* @extends Ext.grid.Panel
* @author Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @docauthor Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @license Dual license available: [GPLv3][1] & commercial. Please contact me for commercial licenses.
* 
* @version 1.3
* 
* [1]: http://www.gnu.org/copyleft/gpl.html
* 
* Provides a pivot grid useful for reporting.
* The component works with Extjs 4.1.x. 
* 
* Changelog:
* 
* 21.08.2012 - v1.1 - Changed the grid to allow expanding/collapsing the row groups.
* 
* 22.08.2012 - v1.2 - Created a summary feature to allow row groups to summarize the values. (sum, avg, min, max, count).
* 
* 18.09.2012 - v1.3:
*   - Added "enableGrouping" to use grouping or not.
*   - Added "refresh" method to reconfigure the pivot grid.
*   - Added "expandAll" and "collapseAll" methods.
*   - Added the posibility to sort the dimensions (leftAxis/topAxis records).
*   - Fixed the aggregator function so that if no record is available it returns an empty string.
*
* 
#Example usage:#

{@img Ext.ux.grid.mzPivotGrid1.png Ext.ux.grid.mzPivotGrid component}

     var grid = Ext.create('Ext.ux.grid.mzPivotGrid', {
        renderTo:       document.body,
        title:          'Pivot grid',
        height:         300,
        width:          600,
        enableLocking:  false,
        viewConfig: {
            trackOver:      true,
            stripeRows:     false
        },
        
        tbar: [{
            xtype:  'button',
            text:   'refresh',
            handler: function(){
                grid.getStore().load();
            }
        }],
        store:  new Ext.data.ArrayStore({
            proxy: {
                type:       'ajax',
                url:        'pivot.json',
                reader: {
                    type:       'array'
                }
            },
            autoLoad:   true,
            fields: [
                'economy', 'region', 'year',
                {name: 'procedures', type: 'int'},
                {name: 'time',       type: 'int'}
            ]
        }),
        
        aggregate: [{
            measure:    'time',
            header:     'Time',
            aggregator: 'sum',
            align:      'right',
            renderer:   Ext.util.Format.numberRenderer('0')
        },{
            measure:    'procedures',
            header:     'Procedures',
            aggregator: 'sum',
            align:      'right',
            renderer:   Ext.util.Format.numberRenderer('0')
        }],
        
        leftAxisTitle:  'Some report',
        leftAxis: [{
            width:      90,
            dataIndex:  'region',
            header:     'Region'
        },{
            width:      90,
            dataIndex:  'economy',
            header:     'Country'
        }],
        
        topAxis: [{
            dataIndex:  'year',
            width:      80
        }]
    });

    

*/
Ext.define('Ext.ux.grid.mzPivotGrid', {
    extend: 'Ext.grid.Panel',
    requires: ['Ext.ux.view.mzPivotChunker', 'Ext.ux.grid.column.mzPivotColumn'],
    alias: 'widget.mzpivotgrid',
    alternateClassName: 'Ext.grid.mzPivotGrid',

    /**
    * @cfg {Boolean} loadMask Set this on false if you don't want to see the loading mask.
    */
    loadMask:           true,
    /**
    * @cfg {Boolean} enableLocking Set this on false if you don't want to lock the left axis columns.
    */
    enableLocking:      true,
    /**
    * @cfg {Boolean} columnLines Set this on false if you don't want to show the column lines.
    */
    columnLines:        true,
    /**
    * @cfg {Boolean} enableGrouping Set this on true if you want to group rows/cols.
    */
    enableGrouping:     false,
    
    /**
    * @cfg {String} leftAxisTitle Set the pivot grid title used above the left axis columns.
    */
    leftAxisTitle:      '&nbsp;',
    
    /**
    * @cfg {Array} leftAxis Define the left axis used by the grid.
    * 
#Example usage:#

        leftAxis: [{
            width:      80,         // column width in the grid
            dataIndex:  'person',   // field used for extracting data from the store
            header:     'Persons'   // column title
        },{
            width:      90,
            dataIndex:  'quarter',
            header:     'Quarter'
        },{
            width:      90,
            dataIndex:  'product',
            header:     'Products'
        }]

    */
    leftAxis:           [],
    /**
    * @cfg {Array} topAxis Define the top axis used by the grid.
    * 
#Example usage:#

        topAxis: [{
            width:      80,         // column width in the grid
            dataIndex:  'city'      // field used for extracting data from the store
        }]
        
    * 
    */
    topAxis:            [],
    /**
    * @cfg {Array} aggregate Define the fields you want to aggregate in the pivot grid. You can have one or multiple fields.
    * 
#Example usage:#

        aggregate: [{
            measure:    'value',        // what field is aggregated
            header:     'Total',        // column title
            aggregator: 'sum',          // function used for aggregating
            align:      'right'         // grid cell alignment
        },{
            measure:    'quantity',
            header:     'Quantity',
            aggregator: 'sum',
            align:      'right',
            renderer:   Ext.util.Format.numberRenderer('0') // grid cell renderer
        }]
        
    * 
    */
    aggregate:          [],
    
    defaultColConfig: {
        menuDisabled:   true,
        sortable:       false        
    },
    
    /**
    * @private override
    */
    initComponent : function(){
        var me = this;
        
        me.columns = [];
        me.viewConfig = Ext.apply({
            xtype:      'mzpivotview',
            // the viewer chunker is handling the grid template
            chunker:    Ext.ux.view.mzPivotChunker,
            pivotGrid:  me
        }, me.viewConfig);
        
        if(me.enableLocking){
            me.lockedGridConfig = {
                selModel:   Ext.create('Ext.selection.CellModel')
            };            
        }

        me.callParent(arguments);

        // bind the viewer to a dummy store
        me.getView().bindStore({
            fields:     []
        });
        
        me.getView().on('itemclick', me.onItemClick);
        
        // clone the original store to make the refresh work
        me.originalStore = Ext.clone(me.store);
        
    },
    
    /**
    * Refresh the pivot grid using the new axis and aggregates. It does not refresh the store assigned to it.
    * 
    */
    refresh: function(){
        var me = this;
        
        me.initPivotGrid(me.getStore().getRange());
    },
    
    /**
    * Expand all groups if enableGrouping is true.
    * 
    */
    expandAll: function(){
        if(!this.enableGrouping) return;
        
        var me = this, 
        viewL = (me.enableLocking) ? me.getView().lockedView.getEl() : me.getView().getEl(), 
        viewR = me.getView().getEl();

        sall = 'tr';
        viewL.select(sall).removeCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
        viewL.select(sall).removeCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
        viewL.select(sall).set({rowVisible: 1});
        viewL.select(sall).set({collapsed: 0});
        if(viewL != viewR){
            viewR.select(sall).removeCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
            viewR.select(sall).removeCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
            viewR.select(sall).set({rowVisible: 1});
            viewR.select(sall).set({collapsed: 0});
        }
    },
    
    /**
    * Collapse all groups if enableGrouping is true.
    * 
    */
    collapseAll: function(){
        if(!this.enableGrouping) return;
        
        var me = this, 
        viewL = (me.enableLocking) ? me.getView().lockedView.getEl() : me.getView().getEl(), 
        viewR = me.getView().getEl();

        sgroups = 'tr[rowData=][class*=grid-row-summary]';
        sall = 'tr[rowData*=_]';

        viewL.select(sgroups).addCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
        viewL.select(sgroups).set({collapsed: 1});
        viewL.select(sgroups).set({rowVisible: 0});

        viewL.select(sall).addCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
        viewL.select(sall).addCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
        viewL.select(sall).set({rowVisible: 0});
        viewL.select(sall).set({collapsed: 1});

        if(viewL != viewR){
            viewR.select(sgroups).addCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
            viewR.select(sgroups).set({collapsed: 1});
            viewR.select(sgroups).set({rowVisible: 0});

            viewR.select(sall).addCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
            viewR.select(sall).addCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
            viewR.select(sall).set({rowVisible: 0});
            viewR.select(sall).set({collapsed: 1});
        }
    },

    /**
    * @private override
    */
    afterRender: function(comp, options){
        var me = this;
        if(me.loadMask){
            me.setLoading({msg: 'Loading...'}, true).bindStore(me.getStore());
        }
        me.callParent(arguments);
        me.getStore().on('load', me.refresh, me);
    },
    
    /**
    * @private override
    */
    getStore: function(){
        return this.originalStore;
    },
    
    /**
    * @private 
    */
    getAggregate: function(){
        var me = this, agg = [], i;
        
        if(Ext.isArray(me.aggregate)){
            agg = me.aggregate;
        }else{
            agg.push(me.aggregate);
        }
        
        for(i = 0; i < agg.length; i++){
            if (typeof agg[i].aggregator == 'string') {
                agg[i].aggregator = Ext.ux.grid.mzAggregatorMgr.types[agg[i].aggregator];
            }
        }
        return agg;
    },
    
    /**
    * @private 
    */
    initPivotGrid: function(records){
        var me = this,
            cells = [],
            countCols = 0,
            storeData,
            agg = me.getAggregate();
        
        if(agg.length == 0){
            throw new Error("[Ext.ux.grid.mzPivotGrid] No aggregates were specified!");
        }
        
        // add default sorters if none is specified
        Ext.Array.forEach(me.leftAxis, function(item, index, len){
            Ext.applyIf(item, {
                direction:  'ASC'
            });
        });
        Ext.Array.forEach(me.topAxis, function(item, index, len){
            Ext.applyIf(item, {
                direction:  'ASC'
            });
        });
        
        me.topTuples = me.getTuples(me.topAxis);
        me.leftTuples = me.getTuples(me.leftAxis);
        
        me.colFields = [];

        me.columns = [Ext.apply({
            text:           me.leftAxisTitle,
            columns:        [],
            menuDisabled:   true,
            locked:         true,
            width:          0
        }, me.defaultColConfig)];
        
        Ext.Array.forEach(me.leftAxis, function(item, index, len){
            me.columns[0].columns.push(Ext.apply({
                text:       item.header,
                xtype:      'mzpivotcolumn',
                dataIndex:  item.dataIndex,
                width:      item.width || 50,
                align:      item.align || 'left',
                pivotGrid:  me
            }, me.defaultColConfig));
            me.colFields.push(item.dataIndex);
            me.colFields.push({
                name:           'rowspan' + item.dataIndex,
                defaultValue:   0
            });
            me.colFields.push({
                name:           'colspan' + item.dataIndex,
                defaultValue:   0
            });
        });
        
        me.colFields.push({
            name:           'rowGroup',
            defaultValue:   0
        });
        me.colFields.push('rowData', 'rowGroupField');
        
        // fix the width of the header column
        Ext.Array.forEach(me.columns[0].columns, function(item, index, len){
            me.columns[0].width += item.width;
        });
        
        me.countCols = 0;
        me.topTuples.each(function(item, index, len){
            me.buildColumns(me.columns, item.data, item.width, agg);
        }, me);

        storeData = Ext.create('Ext.data.ArrayStore', {
            fields:     me.colFields
        });
        cells = me.processRecords(records);
        me.fixRowSpan(cells);
        
        cells = me.fixGroups(cells);
        
        storeData.loadData(cells);
        me.getView().bindStore(storeData, false);
        
        me.reconfigure(storeData, me.columns);
    },
    
    /**
    * @private Build grid columns based on the topTuples
    */
    buildColumns: function(columns, item, width, aggregate){
        var me = this, i, found = false, keys = Ext.Object.getKeys(item), col;
        
        if(keys.length == 0) return;
        for(i = 0; i < columns.length; i++){
            col = columns[i];
            if( col.text == item[keys[0]] ){
                if(!col['columns']) col['columns'] = [];
                delete item[keys[0]];
                me.buildColumns(col['columns'], item, width, aggregate);
                found = true;
                break;
            }
        }
        if(!found){
            col = Ext.apply({
                text:   item[keys[0]],
                width:  width[keys[0]]
            }, me.defaultColConfig);
            if(keys.length == 1){
                // add aggregate columns if there are more than 1
                if(aggregate.length > 1){
                    for(i = 0; i < aggregate.length; i++){
                        if(!col['columns']) col['columns'] = [];
                        col.columns.push(Ext.apply({
                            text:       aggregate[i].header,
                            align:      aggregate[i].align || 'right',
                            width:      aggregate[i].width || 50,
                            dataIndex:  'c' + me.countCols,
                            renderer:   aggregate[i].renderer || Ext.util.Format.numberRenderer('0.00')
                        }, me.defaultColConfig));
                        me.colFields.push('c' + me.countCols);
                        me.countCols++;
                    }
                }else{
                    col.dataIndex = 'c' + me.countCols;
                    col.renderer = aggregate[0].renderer || Ext.util.Format.numberRenderer('0.00');
                    col.align = aggregate[0].align || 'right';
                    me.colFields.push(col.dataIndex);
                    me.countCols++;
                }
            }
            columns.push(col);
            if(keys.length > 1){
                delete item[keys[0]];
                if(!col['columns']) col['columns'] = [];
                me.buildColumns(col['columns'], item, width, aggregate);
            }
        }
    },
    
    /**
    * @private 
    */
    processRecords: function(records){
        var me = this,
            leftTuples = me.leftTuples,
            leftCount  = leftTuples.length,
            topTuples  = me.topTuples,
            topCount   = topTuples.length,
            aggregate = me.getAggregate(),
            cells = [], i, j, k, obj = {}, row;
        
        for (i = 0; i < records.length; i++) {
            record = records[i];
            
            for (j = 0; j < leftCount; j++) {
                cells[j] = cells[j] || [];
                
                if (leftTuples.getAt(j).matcher(record) === true) {
                    for (k = 0; k < topCount; k++) {
                        cells[j][k] = cells[j][k] || [];
                        
                        if (topTuples.getAt(k).matcher(record)) {
                            cells[j][k].push(record);
                        }
                    }
                }
            }
        }
        
        for (i = 0; i < cells.length; i++) {
            row = cells[i];
            
            obj = {};
            me.countCols = 0;
            for (j = 0; j < row.length; j++) {
                for( k = 0; k < aggregate.length; k++){
                    obj['c' + me.countCols] = aggregate[k].aggregator(cells[i][j], aggregate[k].measure);
                    me.countCols++;
                }
            }
            
            Ext.apply(obj, leftTuples.getAt(i).data);
            
            cells[i] = obj;
        }
        
        return cells;
    },
    
    /**
    * @private 
    */
    getTuples: function(dimensions){
        var me = this,
            records = me.getStore().data,
            tuples = new Ext.util.MixedCollection(),
            data = {}, width = {}, i, j, sorters = [];
            
        tuples.getKey = function(el){
            var key = '', ii;
            for(ii=0; ii < dimensions.length; ii++){
                key += el.data[dimensions[ii].dataIndex] + '_';
            }
            return key;
        };

        //creates a specialised matcher function for a given tuple. The returned function will return
        //true if the record passed to it matches the dataIndex values of each dimension in this axis
        var createMatcherFunction = function(data) {
            return function(record) {
                for (var dataIndex in data) {
                    if (record.get(dataIndex) != data[dataIndex]) {
                        return false;
                    }
                }
                
                return true;
            };
        };

        for(i=0; i < records.length; i++){
            data = {};
            width = {};
            for(j=0; j < dimensions.length; j++){
                data[dimensions[j].dataIndex] = records.getAt(i).get(dimensions[j].dataIndex);
                width[dimensions[j].dataIndex] = dimensions[j].width || 100;
            }
            tuples.add({
                data:   Ext.clone(data),
                width:  width,
                matcher: createMatcherFunction(data)
            });
        }
        
        // add sorters
        for(i=0; i < dimensions.length; i++){
            sorters.push({
                property:   dimensions[i].dataIndex,
                direction:  dimensions[i].direction,
                root:       'data'
            })
        }
        
        tuples.sort(sorters);
        return tuples;
    },

    /**
    * @private Fixes the rowspan for the specified index of the tuples collection. It compares all dataIndexes provided.
    * 
    */
    fixRowSpan: function(data){
        var me = this,
            span = {},
            leftAxis = me.leftAxis, cell = {}, i, j, k, newBlock, dimension, end;
        
        newBlock = true;
        for( i = 0; i < data.length; i++){
            cell = data[i];

            if(i == end && i != data.length - 1) {
                newBlock = true;
            }

            if(newBlock){
                span = me.findRowSpan(data, i, leftAxis);
                newBlock = false;
            }

            for( j = 0; j < leftAxis.length; j++){
                dimension = leftAxis[j].dataIndex;
                for (k = 0; k < span[dimension].length; k++){
                    if(j == 0) end = span[dimension][k].end;
                    if(i == span[dimension][k].start){
                        data[i]['rowspan' + dimension] = span[dimension][k].span;
                    }
                    if(i > span[dimension][k].start && i <= span[dimension][k].end){
                        data[i]['rowspan' + dimension] = -1;
                    }
                }
            }
            

            
        }
        
    },
    
    /**
    * @private
    */
    findRowSpan: function(data, index, leftAxis){
        var i, j, span = {}, objP, objN, start, end, prop, changed, dimension, counter;
        
        end = data.length;
        for(i = 0; i < leftAxis.length; i++){
            dimension = leftAxis[i].dataIndex;
            span[dimension] = [];
            counter = 0;
            
            if(i < leftAxis.length - 1){
                start = index;
                for(j = index + 1; j < data.length; j++){
                    objP = data[j-1];
                    if(!objP) objP = data[index];
                    objN = data[j];
                    
                    changed = objP[dimension] != objN[dimension];
                    if(i > 0 && j > 0){
                        changed = changed || objN[leftAxis[i-1].dataIndex] != objP[leftAxis[i-1].dataIndex];
                    }
                    
                    counter++;
                    if(changed || j == data.length - 1 ){
                        if(i == 0) end = j;
                        span[dimension].push({
                            start:  start,
                            end:    j,
                            span:   (j == data.length - 1) ? counter + 1: counter
                        });
                        start = j;
                        counter = 0;
                    }
                    
                    if(j >= end) {
                        break;
                    }
                }
                
            }
            
        }
                
        return span;
    },
    
    /**
    * @private
    */
    onItemClick: function(view, record, item, index, e, eOpts){
        var i, prop, s, sall, elItem = Ext.get(item),
            viewL = view.getEl(), viewR = view.pivotGrid.getView().getEl();
        if(view.lockingPartner) return;
        if(!view.pivotGrid.enableGrouping) return;
        
        for(i = 0; i < view.pivotGrid.leftAxis.length; i++){
            var prop = view.pivotGrid.leftAxis[i].dataIndex;
            if(prop == record.get('rowGroupField')){
                s = 'tr[rowData=' + record.get('rowData') + record.get(prop) + '_]';
                sall = 'tr[rowData*=' + record.get('rowData') + record.get(prop) + '_][rowVisible=1]';
                if(elItem.getAttribute('collapsed') == "1"){
                    viewL.select(s).removeCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                    viewL.select(sall).removeCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                    viewL.select(s).set({rowVisible: 1});
                    if(viewL != viewR){
                        viewR.select(s).removeCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                        viewR.select(sall).removeCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                        viewR.select(s).set({rowVisible: 1});
                    }
                    
                    elItem.set({collapsed: 0});
                    elItem.removeCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
                }else{
                    viewL.select(s).addCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                    viewL.select(sall).addCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                    viewL.select(s).set({rowVisible: 0});
                    if(viewL != viewR){
                        viewR.select(s).addCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                        viewR.select(sall).addCls(Ext.baseCSSPrefix + 'grid-group-collapsed');
                        viewR.select(s).set({rowVisible: 0});
                    }
                    
                    elItem.set({collapsed: 1});
                    elItem.addCls(Ext.baseCSSPrefix + 'grid-row-collapsed');
                }
            }
        }
    },
    
    /**
    * @private
    */
    fixGroups: function(records){
        var me = this,
            i, j, k, newRow, row, rows = [], leftAxis = me.leftAxis, orig;
        
        for(i = 0; i < records.length; i++){
            row = records[i];
            orig = Ext.clone(row);
            for(j = 0; j < leftAxis.length; j++){
                if(row['rowspan' + leftAxis[j].dataIndex] > 0){
                    newRow = Ext.clone(row);
                    Ext.Object.each(newRow, function(key, value, myself){
                        myself[key] = '';
                    });
                    newRow[leftAxis[j].dataIndex] = row[leftAxis[j].dataIndex];
                    newRow['colspan' + leftAxis[j].dataIndex] = leftAxis.length - j;
                    for(k = j+1; k < leftAxis.length; k++){
                        newRow['colspan' + leftAxis[k].dataIndex] = -1;
                    }
                    
                    for(k = 0; k < j; k++){
                        newRow[leftAxis[k].dataIndex] = orig[leftAxis[k].dataIndex];
                    }

                    newRow['rowGroup'] = leftAxis.length - j;
                    newRow['rowGroupField'] = leftAxis[j].dataIndex;
                    if(j > 0){
                        newRow['rowData'] = '';
                        for(k = 0; k < j; k++){
                            newRow['rowData'] += orig[leftAxis[k].dataIndex] + '_';
                        }
                    }
                    
                    rows.push(newRow);
                }
                row['rowspan' + leftAxis[j].dataIndex] = 0;
            }
            if(j > 1){
                row['rowData'] = '';
                for(k = 0; k < j-1; k++){
                    row['rowData'] += orig[leftAxis[k].dataIndex] + '_';
                }
            }
            row['rowGroup'] = 1;
            row['rowGroupField'] = leftAxis[j-1].dataIndex;
            rows.push(row);
        }
        
        return rows;
    }
    
    
});


Ext.ux.grid.mzAggregatorMgr = new Ext.AbstractManager();

Ext.ux.grid.mzAggregatorMgr.registerType('sum', function(records, measure) {
    var length = records.length,
        total  = 0,
        i;
    
    if(records.length == 0) return 'n/a';
    
    for (i = 0; i < length; i++) {
        total += Ext.Number.from(records[i].get(measure), 0);
    }
    
    return total;
});

Ext.ux.grid.mzAggregatorMgr.registerType('avg', function(records, measure) {
    var length = records.length,
        total  = 0,
        i;
    
    if(records.length == 0) return 'n/a';

    for (i = 0; i < length; i++) {
        total += Ext.Number.from(records[i].get(measure), 0);
    }
    
    return (total / length) || 'n/a';
});

Ext.ux.grid.mzAggregatorMgr.registerType('min', function(records, measure) {
    var data   = [],
        length = records.length,
        i, v;
    
    for (i = 0; i < length; i++) {
        data.push(records[i].get(measure));
    }
    
    v = Ext.Array.min(data);
    return Ext.isEmpty(v) ? 'n/a' : v;
});

Ext.ux.grid.mzAggregatorMgr.registerType('max', function(records, measure) {
    var data   = [],
        length = records.length,
        i;
    
    for (i = 0; i < length; i++) {
        data.push(records[i].get(measure));
    }
    
    v = Ext.Array.max(data);
    return Ext.isEmpty(v) ? 'n/a' : v;
});

Ext.ux.grid.mzAggregatorMgr.registerType('count', function(records, measure) {
    return records.length;
});



/**
* @private
* @class Ext.ux.view.mzPivotView
* @extends Ext.view.Table
* @author Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @docauthor Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @license Dual license available: [GPLv3][1] & commercial. Please contact me for commercial licenses.
* 
* @version 1.1
* 
* [1]: http://www.gnu.org/copyleft/gpl.html
* 
* This class handles the pivot grid view.
* 
* Changelog:
* 
* 18.09.2012 - v1.1
*   - Added the "grid-row-summary" css class to the groups.
* 
*/
Ext.define('Ext.ux.view.mzPivotView', {
    extend: 'Ext.view.Table',
    alias: 'widget.mzpivotview',

    prepareData: function(data, idx, record, orig) {
        var me = this,
            leftAxis = me.pivotGrid.leftAxis,
            o = me.callParent(arguments),
            i;

        if(data['rowGroup'] > 1){
            if(me.pivotGrid.enableGrouping){
                o.rowCls = Ext.baseCSSPrefix + 'grid-row-collapsed';
                for(i = 1; i < leftAxis.length - 1; i++ ){
                    if(!Ext.isEmpty(data[leftAxis[i].dataIndex])){
                        o.rowCls += ' ' + Ext.baseCSSPrefix + 'grid-group-collapsed';
                    }
                }
            }
            o.rowCls += ' ' + Ext.baseCSSPrefix + 'grid-row-summary';
        }else{
            if(me.pivotGrid.enableGrouping){
                if(!Ext.isEmpty(data[leftAxis[leftAxis.length - 1].dataIndex]) && leftAxis.length > 1){
                    o.rowCls = Ext.baseCSSPrefix + 'grid-group-collapsed';
                }
            }
        }
        if(me.pivotGrid.enableGrouping){
            o.rowAttr = 'rowData="' + data['rowData'] + '" collapsed="1" rowVisible="0"';
            o.data = record.getData();
        }
        
        return o;
    }


});


/**
* @class Ext.ux.grid.feature.mzPivotSummary
* @extends Ext.grid.feature.Feature
* @author Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @docauthor Adrian Teodorescu (ateodorescu@gmail.com; http://www.mzsolutions.eu)
* @license Dual license available: [GPLv3][1] & commercial. Please contact me for commercial licenses.
* 
* @version 1.1
* 
* [1]: http://www.gnu.org/copyleft/gpl.html
* 
* This feature allows summary for each grouped row of the pivot grid.
* 
* Changelog:
* 
* 18.09.2012 - v1.1 
*   - Check if the feature is disabled or not
* 
{@img Ext.ux.grid.mzPivotGrid2.png Ext.ux.grid.mzPivotGrid component}
* 
*/
Ext.define('Ext.ux.grid.feature.mzPivotSummary', {
    
    extend: 'Ext.grid.feature.Feature',
    
    alias: 'feature.mzpivotsummary',
    
    /**
    * @cfg {String} summaryType Define the type of summary you want to use. Possible values are: sum, min, max, count 
    * or a function reference.
    * 
    */
    summaryType:    'sum',
    
    getSummaryFunc: function(){
        var me = this;
        
        if (typeof me.summaryType == 'string') {
            return Ext.ux.grid.mzAggregatorMgr.types[me.summaryType];
        }else{
            return me.summaryType;
        }
    },

    collectData: function(records, preppedRecords, startIndex, fullWidth, o) {
        var me = this, i, j, k, record, filters, data, header,
            leftAxis = me.view.pivotGrid.leftAxis,
            cols = me.view.pivotGrid.countCols,
            headers   = me.view.gridDataColumns || me.view.getGridColumns(),
            summary = me.getSummaryFunc(), found = false;
        
        for(i = 0; i < headers.length; i++){
            found = found || headers[i].dataIndex == 'c0';
        }
        if(!found) return o;
        
        if(!me.disabled){
            if(records.length > 0){
                store = records[0].store;
            }
            for(i = 0; i < records.length; i++){
                record = records[i];

                if(record.get('rowGroup') > 1){
                    filters = [];
                    j = -1;
                    do{
                        filters.push({
                            property:   leftAxis[j+1].dataIndex,
                            value:      record.get(leftAxis[j+1].dataIndex)
                        });
                        j++;
                    }while(j < leftAxis.length && leftAxis[j].dataIndex != record.get('rowGroupField'));
                    
                    for(j = 0; j < cols; j++){
                        header = me.findHeader(headers, 'c' + j);
                        if(header){
                            data = me.findMatchedRecords(filters, records);
                            o.rows[i][header.id] = header.renderer(summary(data, 'c' + j));
                            record.data[header.dataIndex] = o.rows[i][header.id];
                        }
                    }
                }
            }
        }
                
        return o;
    },
    
    findHeader: function(headers, dataIndex){
        for(var i=0; i < headers.length; i++){
            if(headers[i].dataIndex == dataIndex) return headers[i];
        }
        return null;
    },
    
    findMatchedRecords: function(filters, records){
        var result = [], i, j, found = false;
        
        for(i = 0; i < records.length; i++){
            if(records[i].get('rowGroup') < 2 ){
                found = true;
                for(j = 0; j < filters.length; j++){
                    found = found && records[i].get(filters[j].property) == filters[j].value;
                }
                if(found){
                    result.push(records[i]);
                }
            }
        }
        return result;
    }

    
});
