Ext.define('apod.store.Pictures', {
    extend: 'Ext.data.Store',
    model: 'apod.model.Picture',
    proxy: {
		type: 'direct',
		directFn: carouselService.readPictures
    }
});