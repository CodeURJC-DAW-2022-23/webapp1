page = 1;

$(window).scroll(function () {
  if ($(window).scrollTop() + $(window).height() == $(document).height()) {
    page++;
    $('.spinner-border').removeClass('invisible').addClass('visible');
    $.get('https://localhost:8443/posts', { page }, data => {
      if (data == '')
        $('.no-posts').removeClass('invisible').addClass('visible');

      $('.post-container').append(data);
      $('.spinner-border').removeClass('visible').addClass('invisible');
    });
  }
});
