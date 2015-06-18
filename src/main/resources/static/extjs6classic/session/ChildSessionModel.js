/**
 * This ViewModel provides data for the ChildSession view.
 */
Ext.define('ChildSessionModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		selectedCustomer: false
	},

	stores: {
		// Define a store of Customer records that links to the Session.
		customers: {
			model: 'Customer',
			autoLoad: true,
			session: true
		}
	}
});
