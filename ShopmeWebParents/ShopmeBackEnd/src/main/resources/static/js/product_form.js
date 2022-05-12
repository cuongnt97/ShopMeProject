    dropdownBrand = $("#brand");
    dropdownCategory = $("#category");

    $(document).ready(function(){

        $("#shortDescription").richText();
        $("#fullDescription").richText();

        dropdownBrand.change(function() {
            dropdownCategory.empty();
            getCategories();
        });
        getCategories();

        $("#extraImage1").change(function(){
            if (!checkFileSize(this)) {
                return;
            }
            showExtraImageThumbnail(this);
        });
    });

    function showExtraImageThumbnail(fileInput) {
        var file = fileInput.files[0];
            var reader = new FileReader();
            reader.onload = function(e){
                $("#extraThumbnail1").attr("src", e.target.result);
            };
            reader.readAsDataURL(file);

    }

    function getCategories() {
        brandId = dropdownBrand.val();
        url = brandModuleURL + "/" + brandId + "/categories";
        $.get(url, function(responseJson) {
            $.each(responseJson, function(index, category){
                $("<option>").val(category.id).text(category.name).appendTo(dropdownCategory);
            })
        });
    }

    function checkUnique(form) {
		prodId = $("#id").val();
		prodName = $("#name").val();

		csrfValue = $("input[name='_csrf']").val();

		url = "[[@{/products/check_unique}]]";

		params = {id: Number(prodId), name: prodName, _csrf: csrfValue};

		$.post(url, params, function(response) {
			if (response == "OK") {
				form.submit();
			} else if (response == "Duplicated") {
				showWarningModal("Another product has name " + prodName);
			} else {
				showErrorModal("Unknown response from server");
			}

		}).fail(function() {
			showErrorModal("Could not connect to the server");
		});

		return false;
	}

