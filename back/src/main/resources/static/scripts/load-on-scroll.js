page = 1;

$(window).scroll(function () {
  if ($(window).scrollTop() + $(window).height() == $(document).height()) {
    $('.spinner-border').removeClass('invisible').addClass('visible');
    $.get('https://localhost:8443/posts', { page }, data => {
      console.log(data);
    });
  }
});
