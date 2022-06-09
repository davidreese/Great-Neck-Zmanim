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