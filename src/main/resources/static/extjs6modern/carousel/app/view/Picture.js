Ext.define('apod.view.Picture', {
    extend: 'Ext.Img',
    xtype: 'apodimage',
    
    config: {
        picture: null
    },
    
    updatePicture: function(picture) {
        this.setSrc(picture.get('image') + '&width=' + window.innerWidth +'&height=' + window.innerHeight);
    }
});