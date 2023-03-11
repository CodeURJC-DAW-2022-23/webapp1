var page = 0;
var url;

$(document).ready(() => {
  // fetch 1st page

  url = window.location.href + 'posts';
  getData(url, page);

  if ($('.post-container').length < 2) showBottomReached();
});

// fetch more posts when bottom is reached
$(window).scroll(function () {
  if ($(window).scrollTop() + $(window).height() == $(document).height()) {
    page++;
    $('.spinner-border').removeClass('invisible').addClass('visible');
    getData(url, page);
  }
});

// fetch data
const getData = (url, page) => {
  $.get(url, { page }, data => {
    if (data == '') showBottomReached();

    $('.post-container').append(data);
    $('.spinner-border').removeClass('visible').addClass('invisible');
  });
};

// show message: No more posts :(
const showBottomReached = () => {
  $('.no-posts').removeClass('invisible').addClass('visible');
};