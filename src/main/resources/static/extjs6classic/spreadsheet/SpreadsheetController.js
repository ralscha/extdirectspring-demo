Ext.define('SpreadsheetController', {
    extend: 'Ext.app.ViewController',

    formatDays: function (value) {
        return (value === 1) ? '1 day' : (value + ' days');
    },

    getSelectionModel: function () {
        var grid = this.getView();
        return grid.getSelectionModel();
    },

    onRefresh: function () {
        var grid = this.getView();
        grid.store.reload();
    },

    onSelectionChange: function (grid, selection) {
        var status = this.lookupReference('status'),
            message = '??',
            range, tl, br;

        if (!selection) {
            message = 'No selection';
        }
        else if (selection.isCells) {
            range = selection.getRange();
            tl = range[0];
            br = range[1];

            message = 'Selected cells: ' + (br[0] - tl[0] + 1) + 'x' + (br[1] - tl[1] + 1) +
                ' at (' + tl[0] + ',' + tl[1] + ')';
        }
        else if (selection.isRows) {
            message = 'Selected rows: ' + selection.getCount();
        }
        else if (selection.isColumns) {
            message = 'Selected columns: ' + selection.getCount();
        }

        status.update(message);
    },

    toggleRowSelect: function(button, pressed) {
        var sel = this.getSelectionModel();
        sel.setRowSelect(pressed);
    },

    toggleCellSelect: function(button, pressed) {
        var sel = this.getSelectionModel();
        sel.setCellSelect(pressed);
    },

    toggleColumnSelect: function(button, pressed) {
        var sel = this.getSelectionModel();
        sel.setColumnSelect(pressed);
    }
});
