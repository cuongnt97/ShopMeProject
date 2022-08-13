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
    });

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

		params = {id: Number(prodId), name: prodName, _csrf: csrfValue};

		$.post(checkUniqueUrl, params, function(response) {
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

