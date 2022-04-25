package com.shopme.admin.brand;

import com.shopme.admin.common.AbtractExporter;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BrandCSVExporter extends AbtractExporter {

    public void exportCSV(List<Brand> listBrands, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv", "brand_");
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Brand ID", " Brand Name", "Categories"};

        String[] fieldMapping = {"id", "name", "categories"};

        csvWriter.writeHeader(csvHeader);

        for (Brand brand : listBrands) {
            csvWriter.write(brand, fieldMapping);
        }
        csvWriter.close();

    }
}
