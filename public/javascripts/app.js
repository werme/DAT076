;

(function(){

  e = $('#quick-create-form');
  e.css({
    "top": -e.height(),
    "margin-bottom": -e.height()
  });

  var toggleMenu = function() {
    e.css("display", "block");

    if(e.css('top') === '0px') {
      e.css('top', -e.height());
      e.css('margin-bottom', -e.height());
    } else {
      e.css('top', 0);
      e.css('margin-bottom', 0);
    }
  };

  $('#sub-nav-toggle').click(function() {
    toggleMenu();
  });

})();
