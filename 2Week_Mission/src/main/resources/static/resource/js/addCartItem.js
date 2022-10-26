let index = {
    init: function () {
        $("#addItemBtn").on("click", () => {
            this.addItem();
        });
    },

    addItem: function () {

        let productId = $("#productId").val();

        $.ajax({
            url: "/cart/add/" + productId,
            type: "POST",
            contentType: "application/json; charset=utf-8",
            success: function (result) {

                if (confirm("해당 도서가 장바구니에 추가되었습니다. 장바구니로 이동하시겠습니까?")) {
                    location.href = "/cart/list";
                } else {
                    location.href = "/product/list";
                }

            },
            error: function (error) {

                console.log(JSON.stringify(error));

            }
        });

    },
}

index.init();