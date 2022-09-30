$(function () {
  $('[data-toggle="popover"]').popover()
})

$(function () {
  $('.example-popover').popover({
    container: 'body'
  })
})

//$(function () {
//  $('[data-toggle="tooltip"]').tooltip()
//})

$('.popover-dismiss').popover({
  trigger: 'focus'
})

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const start = urlParams.get('st');
if (start == "sh") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-shacharit-tab").classList.add("active");
    document.getElementById("pills-shacharit").classList.add("active", "show");
} else if (start == "mi") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-mincha-tab").classList.add("active");
    document.getElementById("pills-mincha").classList.add("active", "show");
} else if (start == "ar") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-arvit-tab").classList.add("active");
    document.getElementById("pills-arvit").classList.add("active", "show");
} else if (start == "se") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-selichot-tab").classList.add("active");
    document.getElementById("pills-selichot").classList.add("active", "show");
} else if (start == "mr") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-megila-tab").classList.add("active");
    document.getElementById("pills-megila").classList.add("active", "show");
}