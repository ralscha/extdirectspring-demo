Ext.Loader.setConfig({
    enabled: true,
    paths: {
        'carstore': 'app'
    }
});

Ext.onReady( function () {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
	
    Deft.Injector.configure({
        carChart: 'carstore.store.CarChart',
        carData: 'carstore.store.CarData'
    });
    
    Ext.create('carstore.view.CarListings');
});
