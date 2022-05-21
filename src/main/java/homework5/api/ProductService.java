package homework5.api;

import homework5.dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductService {

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequestPOST);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<Product> modifyProduct(@Body Product modifyProductRequestPOST);

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @GET("products")
    Call<ResponseBody> getProducts();

}
