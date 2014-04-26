Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();

    

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function change(val) {
        if (val > 0) {
            return '<span style="color:green;">' + val + '</span>';
        } else if (val < 0) {
            return '<span style="color:red;">' + val + '</span>';
        }
        return val;
    }

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function pctChange(val) {
        if (val > 0) {
            return '<span style="color:green;">' + val + '%</span>';
        } else if (val < 0) {
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }

    var store = Ext.create('Ext.data.Store', {
        model: 'Are.Company',
        autoLoad: true,
        remoteFilter: true
    });

    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        columnLines: true,
        plugins:[
            // When remotefilter is active it will 
            // send with the datastore the "search variables"
			Ext.create('Ext.ux.grid.xFilterRow',{
				remoteFilter:false
			})
		],
        columns: [{
            text     : 'Company',
            flex     : 1,
            sortable : false,
            dataIndex: 'company',
            xfilter:{
            	xtype:'textfield'
            }
        }, {
            text: 'Stock Price',
            columns: [{
                text     : 'Price',
                width    : 75,
                sortable : true,
                renderer : 'usMoney',
                dataIndex: 'price'
            }, {
                text     : 'Change',
                width    : 75,
                sortable : true,
                renderer : change,
                dataIndex: 'change'
            }, {
                text     : '% Change',
                width    : 75,
                sortable : true,
                renderer : pctChange,
                dataIndex: 'pctChange'
            }]
        }, {
            text     : 'Last Updated',
            width    : 85,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('m/d/Y'),
            dataIndex: 'lastChange',
           	xfilter:{
            	xtype:'datefield',
            	format:'m/d/Y'
            }
        }],
        height: 350,
        width: 600,
        title: 'Grouped Header Grid',
        renderTo: 'grid-example',
        viewConfig: {
            stripeRows: true
        }
    });
});
