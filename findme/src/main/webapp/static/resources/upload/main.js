require.config({
    paths: {
        jquery: '../lib/jquery/jquery-1.11.3'
    }
});
 
require(['jquery'], function($) {
    alert($().jquery);
});