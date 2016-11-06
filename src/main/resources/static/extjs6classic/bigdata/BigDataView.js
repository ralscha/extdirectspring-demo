/**
 * This is an example of using the ExtJS grid to show very large datasets
 * without overloading the DOM. It also uses locking columns, and incorporates the
 * GroupSummary feature. Filtering is enabled on certain columns using the FilterFeature.
 *
 * As an illustration of the ability of grid columns to act as containers, the Title
 * column has a filter text field built in which filters as you type.
 *
 * The grid is editable using the RowEditing plugin.
 *
 * The `multiColumnSort` config is used to allow multiple columns to have sorters.
 */
Ext.define('BigDataView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'Ext.grid.filters.Filters'
    ],
    store: 'BigDataStore',
    columnLines: true,
    height: 400,
    width: 910,
    title: 'Editable Big Data Grid',
    multiColumnSort: true,
    syncRowHeight: false,
    controller: 'bigdata',

    features: [{
        ftype : 'groupingsummary',
        groupHeaderTpl : '{name}',
        hideGroupedHeader : false,
        enableGroupingMenu : false
    }, {
        ftype: 'summary',
        dock: 'bottom'
    }],

    lockedGridConfig: {
        title: 'Employees',
        header: false,
        collapsible: true,
        width: 325,
        minWidth: 290,
        forceFit: true
    },

    selModel: {
        type: 'checkboxmodel',
        checkOnly: true
    },
    
    listeners: {
        headermenucreate: 'onHeaderMenuCreate',
        // this event notifies us when the document was saved
        documentsave: 'onDocumentSave',
        beforedocumentsave: 'onBeforeDocumentSave'
    },

    columns:[{
        xtype: 'rownumberer',
        width: 40,
        sortable: false,
        locked: true
    }, {
        text: 'Id',
        sortable: true,
        dataIndex: 'employeeNo',
        groupable: false,
        width: 80,
        locked: true,
        editRenderer: 'bold'
    }, {
        text: 'Name (Filter)',
        dataIndex: 'name',
        sortable: true,
        sorter: {
            sorterFn: 'nameSorter' // set controller
        },

        width: 140,
        groupable: false,

        layout: 'hbox',
        locked: true,
        renderer: 'concatNames',
        editor: {
            xtype: 'textfield'
        },
        items: {
            xtype: 'textfield',
            reference: 'nameFilterField',
            flex : 1,
            margin: 2,
            enableKeyEvents: true,
            listeners: {
                keyup: 'onNameFilterKeyup',
                buffer: 500
            }
        }
    }, {
        text: 'Rating',
        width: 100,
        sortable: true,
        dataIndex: 'rating',
        groupable: false,
        xtype: 'widgetcolumn',
        widget: {
            xtype: 'sparklineline'
        }
    }, {
        text: 'Date of birth',
        dataIndex: 'dob',
        xtype: 'datecolumn',
        groupable: false,
        width: 115,
        filter: {

        },
        editor: {
            xtype: 'datefield'
        },
        exportStyle: {
            alignment: {
                horizontal: 'Right'
            },
            format: 'Long Date'
        }
    }, {
        text: 'Join date',
        dataIndex: 'joinDate',
        xtype: 'datecolumn',
        groupable: false,
        width: 120,
        filter: {

        },
        editor: {
            xtype: 'datefield'
        },
        exportStyle: {
            alignment: {
                horizontal: 'Right'
            },
            format: 'Long Date'
        }
    }, {
        text: 'Notice<br>period',
        dataIndex: 'noticePeriod',
        groupable: false,
        width: 115,
        filter: {
            type: 'list'
        },
        editor: {
            xtype: 'combobox',
            listeners: {
                beforerender: 'onBeforeRenderNoticeEditor'
            }
        }
    }, {
        text: 'Email address',
        dataIndex: 'email',

        width: 200,
        groupable: false,
        renderer: 'renderMailto',
        editor: {
            xtype: 'textfield'
        }
    }, {
        text: 'Department',
        dataIndex: 'department',
        hidden: true,
        hideable: false,
        filter: {
            type: 'list'
        }
    }, {
        text: 'Absences',
        shrinkWrap: true,
        columns: [{
            text: 'Illness',
            dataIndex: 'sickDays',
            width: 100,
            groupable: false,
            summaryType: 'sum',
            summaryFormatter: 'number("0")',
            filter: {

            },
            editor: {
                xtype: 'numberfield',
                decimalPrecision: 0
            }
        }, {
            text: 'Holidays',
            dataIndex: 'holidayDays',
            width: null, // Size column to title text
            groupable: false,
            summaryType: 'sum',
            summaryFormatter: 'number("0")',
            filter: {

            },
            editor: {
                xtype: 'numberfield',
                decimalPrecision: 0
            }
        }, {
            text: 'Holiday Allowance',
            dataIndex: 'holidayAllowance',
            width: null, // Size column to title text
            groupable: false,
            summaryType: 'sum',
            summaryFormatter: 'number("0.00")',
            formatter: 'number("0.00")',
            filter: {

            },
            editor: {
                xtype: 'numberfield',
                decimalPrecision: 0
            }
        }]
    }, {
        text: 'Salary',
        width: 155,
        sortable: true,
        dataIndex: 'salary',
        align: 'right',
        formatter: 'usMoney',
        groupable: false,
        summaryType: 'average',
        summaryFormatter: 'usMoney',
        filter: {

        },
        editor: {
            xtype: 'numberfield',
            decimalPrecision: 2
        },
        exportStyle: {
            alignment: {
                horizontal: 'Right'
            },
            format: 'Currency'
        }
    }],

    viewConfig: {
        stripeRows: true
    },
    
    plugins: [{
        ptype: 'gridfilters'
    }, {
        ptype: 'rowexpander',

        // dblclick invokes the row editor
        expandOnDblClick: false,
        rowBodyTpl: '<img src="{avatar}" height="100px" style="float:left;margin:0 10px 5px 0"><b>{name}<br></b>{dob:date}'
    }]
});
