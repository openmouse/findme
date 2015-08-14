require.config({
    paths: {
        jquery: '../lib/jquery/jquery-1.11.3',
        jqueryfileupload : '../lib/fileupload/js/jquery.fileupload',
        widget:'../lib/fileupload/js/vendor/jquery.ui.widget',
        transport:'..lib/fileupload/js/jquery.iframe-transport'
    },
    'jqueryfileupload' : {
        deps : ["jquery","widget","transport"]           
    }
});
 
require(['jquery','jqueryfileupload'], function($) {
    alert($().jquery);
});