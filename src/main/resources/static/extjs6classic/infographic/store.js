Ext.define('Infographic.Store', {
    extend: 'Ext.data.Store',
    alias: 'store.unemployment',
    fields: ['label', 'span', 'y2007', 'y2008', 'y2009', 'y2010', 'y2011', 'y2012', 'state'],
    autoLoad: true,
	proxy: {
		type: 'direct',
		directFn: 'infographicService.getUnemployments'
	}
});