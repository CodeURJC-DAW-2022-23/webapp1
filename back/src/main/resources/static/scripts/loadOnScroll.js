let page = 0;

window.addEventListener('scroll', () => {
  const { scrollTop, clientHeight, scrollHeight } = document.documentElement;
  if (scrollTop + clientHeight >= scrollHeight) {
    page++;
    async () => await fetch('http://localhost:8443/page');

    console.log('User wants more data');
    console.log(page);
  }
});
