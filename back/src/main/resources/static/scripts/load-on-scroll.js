var page = 0;
var url;

$(document).ready(() => {
  // fetch 1st page
  url = window.location.href + 'posts';
  getData(url, 0);
});

$(window).scroll(function () {
  if ($(window).scrollTop() + $(window).height() == $(document).height()) {
    page++;
    $('.spinner-border').removeClass('invisible').addClass('visible');
    getData(url, page);
  }
});

const getData = (url, page) => {
  $.get(url, { page }, data => {
    if (data == '') $('.no-posts').removeClass('invisible').addClass('visible');

    $('.post-container').append(data);
    $('.spinner-border').removeClass('visible').addClass('invisible');
  });
};
