package homework5;

import com.github.javafaker.Faker;
import homework5.api.ProductService;
import homework5.dto.Product;
import homework5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateProductTest {

    static ProductService productService;
    Product product = null;
    Product productUpd = null;
    Faker faker = new Faker();
    int id;
    String productName;
    int productPrice;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        // POST request
        Response<Product> response = productService
                .createProduct(product)
                .execute();
        id =  response.body().getId();
        productName = response.body().getTitle();
        productPrice = response.body().getPrice();
        assertThat(response.isSuccessful(), CoreMatchers.is( true));
        assertThat(id,notNullValue());
        assertThat(productName,notNullValue());
        System.err.println("Created the new Product with Id-" + id);

        // GET Products request
        Response<ResponseBody> response2 = productService
                .getProducts()
                .execute();
        assertThat(response2.isSuccessful(), CoreMatchers.is( true));
        System.err.println("The list of all Products have been got");

        // GET Product by Id request (Positive)
        Response<Product> response3 = productService
                .getProductById(id)
                .execute();
        assertThat(response3.isSuccessful(), CoreMatchers.is( true));
        assertThat(response3.body().getId(), equalTo(id));
        assertThat(response3.body().getTitle(), equalTo(productName));
        assertThat(response3.body().getPrice(), equalTo(productPrice));
        assertThat(response3.body().getCategoryTitle(), equalTo("Food"));
        System.err.println("All details about Product with Id-" + id + " have been got");

        // GET Product by Id request (Negative)
        Response<Product> response4 = productService
                .getProductById(5000)
                .execute();
        assertThat(response4.isSuccessful(), CoreMatchers.is( false));
        System.err.println("The Product with id: 5000 doesn't exist");

        // PUT Modify Product request (Positive)
        productUpd = new Product()
                .withId(id)
                .withTitle(productName)
                .withCategoryTitle("Food")
                .withPrice(555); // измененное поле
        Response<Product> response5 = productService
                .modifyProduct(productUpd)
                .execute();
        assertThat(response5.isSuccessful(), CoreMatchers.is( true));
        assertThat(response5.body().getPrice(), equalTo(555));
        assertThat(response5.body().getId(), equalTo(id));
        assertThat(response5.body().getTitle(), equalTo(productName));
        assertThat(response5.body().getCategoryTitle(), equalTo("Food"));
        System.err.println("The Product with Id-" + id + " have the new price 555");

        // PUT Modify Product request (Negative)
        productUpd = new Product()
                .withId(777)
                .withTitle(productName)
                .withCategoryTitle("Food")
                .withPrice(555); // измененное поле
        Response<Product> response6 = productService
                .modifyProduct(productUpd)
                .execute();
        assertThat(response6.isSuccessful(), CoreMatchers.is( false));
        System.err.println("Product with id: 777 doesn't exist");
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService
                // DELETE Request
                .deleteProduct(id)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        System.err.println("The Product with Id-" + id + " have been deleted");
    }


}
