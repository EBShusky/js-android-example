if(!NativeApp){
    var NativeApp = {
        getGps: function(){
            return {
                start: function(){
                    console.log('gps.start');
                },

                stop: function(){
                    console.log('gps.stop');
                },

                onUpdate: function(method){
                    console.log('gps.onUpdate', method);
                },

                setDelay: function(value){
                    console.log('gps.setDelay', value);
                },

                getDelay: function(){
                    console.log('gps.getDelay');
                    return 3;
                }
            };
        },

        getMath: function(){
            return {
                sum: function(args){
                    console.log('math.sum', args);

                    return args.reduce((prev, current) => {
                        if(typeof current === 'number'){
                            return prev + current;
                        }
                        if(typeof current === 'string'){
                            return prev + current.length;
                        }

                        return prev;
                    }, 0);
                }
            }
        }
    };
}
