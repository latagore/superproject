
function addCart1(){
	alert("a");
}
function increaseQuantity(node){
	var inputbox = document.getElementById(node.value + "_item_quantity");
	inputbox.value++;
}
function decreaseQuantity(node){
	var inputbox = document.getElementById(node.value + "_item_quantity");
	if (inputbox.value > 1){
		inputbox.value--;
	}
}
function addCart()
{
    var itemID = document.getElementById('itemID').value;
    var itemquantity = document.getElementById("item_quantity");
    
    if(srchdata == ""){
        document.location.href="/Front/addCart/id=" + itemID + "&quantity=" + itemquantity;    
    }               
}