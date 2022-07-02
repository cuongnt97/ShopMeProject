    var extraImageCount = 0;
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
        $("input[name='extraImage']").each(function(index){
            extraImageCount++;
            $(this).change(function(){
                showExtraImageThumbnail(this, index);
            });
        });
    });

    function showExtraImageThumbnail(fileInput, index) {
        var file = fileInput.files[0];
            var reader = new FileReader();
            reader.onload = function(e){
                $("#extraThumbnail" + index).attr("src", e.target.result);
            };
            reader.readAsDataURL(file);

            if (index >= extraImageCount - 1){
                addNextExtraImageSection(index + 1);
            }


    }

    function addNextExtraImageSection(index){
        htmlExtraImage = `
            <div class="col border m-3 p-2" id="divExtraImage${index}">
                <div id="extraImageHeader${index}"><label>Extra Image #${index + 1}</label></div>
                <div class="m-3">
                    <img id="extraThumbnail${index}" alt="Extra image #${index + 1} preview" class="img-fluid"
                                 src="${defaultImageThumbnailSrc}"/>
                </div>
                <div>
                    <input type="file" name="extraImage"
                                   onchange="showExtraImageThumbnail(this, ${index})"
                                   accept="image/jpg, image/jpeg"/>
                </div>
            </div>
        `;

        htmlLinkRemove = `
            <a class="btn fas fa-times-circle fa-2x float-right"
                href="javascript:removeExtraImage(${index - 1})"
                title="Remove this image"></a>
        `;

        $("#divProductImages").append(htmlExtraImage);
        $("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);
        extraImageCount++;

    };

    function removeExtraImage(index){
        $("#divExtraImage" + index).remove();
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

