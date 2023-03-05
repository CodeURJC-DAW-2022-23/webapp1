$(window).scroll(function () {
  if ($(window).scrollTop() + $(window).height() == $(document).height()) {
    $('.spinner-border').removeClass('invisible').addClass('visible');
    alert('bottom!');
    // getData();
  }
});
