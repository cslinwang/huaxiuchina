
$(function(){
	$(".menu").on('click',function(){
		//alert(333);
		$(".menu ul").addClass("dis-none")
		console.log(22);
		$(this).find("ul").removeClass("dis-none")
		//$(this).find("ul").removeAttr("display","none")
		$(".menu").find("i").html("+")
		$(this).find("i").html("-")
		console.log(333)
		//$(this).on("click",function(){
		//	$(this).find("ul").toggleClass("dis-none")
		//})
	})
	
})
//点击时候换css;
$(".menu").on("mouseover",function(){
	
	$(this).css("cursor","no-drop")
	
	
})
var b ={};
b._send = function(a,b){
	$(a).on("click",function(){
		$(".content").addClass("dis-none")
		$(b).removeClass("dis-none")
		
		
	})
	
}
b._send("#11","#111");

b._send("#12","#112");
$("#l_out").toggleClass("dis-none")



