
package de.noob.noobservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.noob.noobservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GiveRating_QNAME = new QName("http://noobservice.noob.de/", "giveRating");
    private final static QName _CommentOnLocation_QNAME = new QName("http://noobservice.noob.de/", "commentOnLocation");
    private final static QName _LoginResponse_QNAME = new QName("http://noobservice.noob.de/", "loginResponse");
    private final static QName _ListAllLocationsResponse_QNAME = new QName("http://noobservice.noob.de/", "listAllLocationsResponse");
    private final static QName _ListLocationsWithCategoryResponse_QNAME = new QName("http://noobservice.noob.de/", "listLocationsWithCategoryResponse");
    private final static QName _Logout_QNAME = new QName("http://noobservice.noob.de/", "logout");
    private final static QName _CreateLocationWithImageResponse_QNAME = new QName("http://noobservice.noob.de/", "createLocationWithImageResponse");
    private final static QName _Register_QNAME = new QName("http://noobservice.noob.de/", "register");
    private final static QName _ListLocationsWithCategory_QNAME = new QName("http://noobservice.noob.de/", "listLocationsWithCategory");
    private final static QName _ListLocationsWithNameResponse_QNAME = new QName("http://noobservice.noob.de/", "listLocationsWithNameResponse");
    private final static QName _CommentOnLocationResponse_QNAME = new QName("http://noobservice.noob.de/", "commentOnLocationResponse");
    private final static QName _CreateLocationResponse_QNAME = new QName("http://noobservice.noob.de/", "createLocationResponse");
    private final static QName _SetUserDetailsResponse_QNAME = new QName("http://noobservice.noob.de/", "setUserDetailsResponse");
    private final static QName _DeleteUser_QNAME = new QName("http://noobservice.noob.de/", "deleteUser");
    private final static QName _CreateLocation_QNAME = new QName("http://noobservice.noob.de/", "createLocation");
    private final static QName _ListCategories_QNAME = new QName("http://noobservice.noob.de/", "listCategories");
    private final static QName _GetUserDetails_QNAME = new QName("http://noobservice.noob.de/", "getUserDetails");
    private final static QName _ListCities_QNAME = new QName("http://noobservice.noob.de/", "listCities");
    private final static QName _SetUserDetails_QNAME = new QName("http://noobservice.noob.de/", "setUserDetails");
    private final static QName _ListCitiesResponse_QNAME = new QName("http://noobservice.noob.de/", "listCitiesResponse");
    private final static QName _ListAllLocations_QNAME = new QName("http://noobservice.noob.de/", "listAllLocations");
    private final static QName _ListCategoriesResponse_QNAME = new QName("http://noobservice.noob.de/", "listCategoriesResponse");
    private final static QName _GiveRatingResponse_QNAME = new QName("http://noobservice.noob.de/", "giveRatingResponse");
    private final static QName _ListLocationsWithName_QNAME = new QName("http://noobservice.noob.de/", "listLocationsWithName");
    private final static QName _DeleteUserResponse_QNAME = new QName("http://noobservice.noob.de/", "deleteUserResponse");
    private final static QName _CreateLocationWithImage_QNAME = new QName("http://noobservice.noob.de/", "createLocationWithImage");
    private final static QName _SetLocationDetailsResponse_QNAME = new QName("http://noobservice.noob.de/", "setLocationDetailsResponse");
    private final static QName _Login_QNAME = new QName("http://noobservice.noob.de/", "login");
    private final static QName _GetUserDetailsResponse_QNAME = new QName("http://noobservice.noob.de/", "getUserDetailsResponse");
    private final static QName _SetLocationDetails_QNAME = new QName("http://noobservice.noob.de/", "setLocationDetails");
    private final static QName _LogoutResponse_QNAME = new QName("http://noobservice.noob.de/", "logoutResponse");
    private final static QName _RegisterResponse_QNAME = new QName("http://noobservice.noob.de/", "registerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.noob.noobservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListCategories }
     * 
     */
    public ListCategories createListCategories() {
        return new ListCategories();
    }

    /**
     * Create an instance of {@link GetUserDetails }
     * 
     */
    public GetUserDetails createGetUserDetails() {
        return new GetUserDetails();
    }

    /**
     * Create an instance of {@link ListAllLocations }
     * 
     */
    public ListAllLocations createListAllLocations() {
        return new ListAllLocations();
    }

    /**
     * Create an instance of {@link ListCitiesResponse }
     * 
     */
    public ListCitiesResponse createListCitiesResponse() {
        return new ListCitiesResponse();
    }

    /**
     * Create an instance of {@link SetUserDetails }
     * 
     */
    public SetUserDetails createSetUserDetails() {
        return new SetUserDetails();
    }

    /**
     * Create an instance of {@link ListCities }
     * 
     */
    public ListCities createListCities() {
        return new ListCities();
    }

    /**
     * Create an instance of {@link GiveRatingResponse }
     * 
     */
    public GiveRatingResponse createGiveRatingResponse() {
        return new GiveRatingResponse();
    }

    /**
     * Create an instance of {@link ListCategoriesResponse }
     * 
     */
    public ListCategoriesResponse createListCategoriesResponse() {
        return new ListCategoriesResponse();
    }

    /**
     * Create an instance of {@link DeleteUserResponse }
     * 
     */
    public DeleteUserResponse createDeleteUserResponse() {
        return new DeleteUserResponse();
    }

    /**
     * Create an instance of {@link ListLocationsWithName }
     * 
     */
    public ListLocationsWithName createListLocationsWithName() {
        return new ListLocationsWithName();
    }

    /**
     * Create an instance of {@link CreateLocationWithImage }
     * 
     */
    public CreateLocationWithImage createCreateLocationWithImage() {
        return new CreateLocationWithImage();
    }

    /**
     * Create an instance of {@link GetUserDetailsResponse }
     * 
     */
    public GetUserDetailsResponse createGetUserDetailsResponse() {
        return new GetUserDetailsResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link SetLocationDetailsResponse }
     * 
     */
    public SetLocationDetailsResponse createSetLocationDetailsResponse() {
        return new SetLocationDetailsResponse();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link SetLocationDetails }
     * 
     */
    public SetLocationDetails createSetLocationDetails() {
        return new SetLocationDetails();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link ListAllLocationsResponse }
     * 
     */
    public ListAllLocationsResponse createListAllLocationsResponse() {
        return new ListAllLocationsResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link CommentOnLocation }
     * 
     */
    public CommentOnLocation createCommentOnLocation() {
        return new CommentOnLocation();
    }

    /**
     * Create an instance of {@link GiveRating }
     * 
     */
    public GiveRating createGiveRating() {
        return new GiveRating();
    }

    /**
     * Create an instance of {@link ListLocationsWithCategoryResponse }
     * 
     */
    public ListLocationsWithCategoryResponse createListLocationsWithCategoryResponse() {
        return new ListLocationsWithCategoryResponse();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link CreateLocationWithImageResponse }
     * 
     */
    public CreateLocationWithImageResponse createCreateLocationWithImageResponse() {
        return new CreateLocationWithImageResponse();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link SetUserDetailsResponse }
     * 
     */
    public SetUserDetailsResponse createSetUserDetailsResponse() {
        return new SetUserDetailsResponse();
    }

    /**
     * Create an instance of {@link CreateLocationResponse }
     * 
     */
    public CreateLocationResponse createCreateLocationResponse() {
        return new CreateLocationResponse();
    }

    /**
     * Create an instance of {@link CommentOnLocationResponse }
     * 
     */
    public CommentOnLocationResponse createCommentOnLocationResponse() {
        return new CommentOnLocationResponse();
    }

    /**
     * Create an instance of {@link ListLocationsWithNameResponse }
     * 
     */
    public ListLocationsWithNameResponse createListLocationsWithNameResponse() {
        return new ListLocationsWithNameResponse();
    }

    /**
     * Create an instance of {@link ListLocationsWithCategory }
     * 
     */
    public ListLocationsWithCategory createListLocationsWithCategory() {
        return new ListLocationsWithCategory();
    }

    /**
     * Create an instance of {@link DeleteUser }
     * 
     */
    public DeleteUser createDeleteUser() {
        return new DeleteUser();
    }

    /**
     * Create an instance of {@link CreateLocation }
     * 
     */
    public CreateLocation createCreateLocation() {
        return new CreateLocation();
    }

    /**
     * Create an instance of {@link LocationTO }
     * 
     */
    public LocationTO createLocationTO() {
        return new LocationTO();
    }

    /**
     * Create an instance of {@link UserTO }
     * 
     */
    public UserTO createUserTO() {
        return new UserTO();
    }

    /**
     * Create an instance of {@link UserLoginResponse }
     * 
     */
    public UserLoginResponse createUserLoginResponse() {
        return new UserLoginResponse();
    }

    /**
     * Create an instance of {@link ReturnCodeResponse }
     * 
     */
    public ReturnCodeResponse createReturnCodeResponse() {
        return new ReturnCodeResponse();
    }

    /**
     * Create an instance of {@link CategoryListResponse }
     * 
     */
    public CategoryListResponse createCategoryListResponse() {
        return new CategoryListResponse();
    }

    /**
     * Create an instance of {@link CityListResponse }
     * 
     */
    public CityListResponse createCityListResponse() {
        return new CityListResponse();
    }

    /**
     * Create an instance of {@link LocationListResponse }
     * 
     */
    public LocationListResponse createLocationListResponse() {
        return new LocationListResponse();
    }

    /**
     * Create an instance of {@link CommentTO }
     * 
     */
    public CommentTO createCommentTO() {
        return new CommentTO();
    }

    /**
     * Create an instance of {@link RatingTO }
     * 
     */
    public RatingTO createRatingTO() {
        return new RatingTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GiveRating }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "giveRating")
    public JAXBElement<GiveRating> createGiveRating(GiveRating value) {
        return new JAXBElement<GiveRating>(_GiveRating_QNAME, GiveRating.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommentOnLocation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "commentOnLocation")
    public JAXBElement<CommentOnLocation> createCommentOnLocation(CommentOnLocation value) {
        return new JAXBElement<CommentOnLocation>(_CommentOnLocation_QNAME, CommentOnLocation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAllLocationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listAllLocationsResponse")
    public JAXBElement<ListAllLocationsResponse> createListAllLocationsResponse(ListAllLocationsResponse value) {
        return new JAXBElement<ListAllLocationsResponse>(_ListAllLocationsResponse_QNAME, ListAllLocationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListLocationsWithCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listLocationsWithCategoryResponse")
    public JAXBElement<ListLocationsWithCategoryResponse> createListLocationsWithCategoryResponse(ListLocationsWithCategoryResponse value) {
        return new JAXBElement<ListLocationsWithCategoryResponse>(_ListLocationsWithCategoryResponse_QNAME, ListLocationsWithCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Logout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "logout")
    public JAXBElement<Logout> createLogout(Logout value) {
        return new JAXBElement<Logout>(_Logout_QNAME, Logout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLocationWithImageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "createLocationWithImageResponse")
    public JAXBElement<CreateLocationWithImageResponse> createCreateLocationWithImageResponse(CreateLocationWithImageResponse value) {
        return new JAXBElement<CreateLocationWithImageResponse>(_CreateLocationWithImageResponse_QNAME, CreateLocationWithImageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListLocationsWithCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listLocationsWithCategory")
    public JAXBElement<ListLocationsWithCategory> createListLocationsWithCategory(ListLocationsWithCategory value) {
        return new JAXBElement<ListLocationsWithCategory>(_ListLocationsWithCategory_QNAME, ListLocationsWithCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListLocationsWithNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listLocationsWithNameResponse")
    public JAXBElement<ListLocationsWithNameResponse> createListLocationsWithNameResponse(ListLocationsWithNameResponse value) {
        return new JAXBElement<ListLocationsWithNameResponse>(_ListLocationsWithNameResponse_QNAME, ListLocationsWithNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommentOnLocationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "commentOnLocationResponse")
    public JAXBElement<CommentOnLocationResponse> createCommentOnLocationResponse(CommentOnLocationResponse value) {
        return new JAXBElement<CommentOnLocationResponse>(_CommentOnLocationResponse_QNAME, CommentOnLocationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLocationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "createLocationResponse")
    public JAXBElement<CreateLocationResponse> createCreateLocationResponse(CreateLocationResponse value) {
        return new JAXBElement<CreateLocationResponse>(_CreateLocationResponse_QNAME, CreateLocationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "setUserDetailsResponse")
    public JAXBElement<SetUserDetailsResponse> createSetUserDetailsResponse(SetUserDetailsResponse value) {
        return new JAXBElement<SetUserDetailsResponse>(_SetUserDetailsResponse_QNAME, SetUserDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "deleteUser")
    public JAXBElement<DeleteUser> createDeleteUser(DeleteUser value) {
        return new JAXBElement<DeleteUser>(_DeleteUser_QNAME, DeleteUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLocation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "createLocation")
    public JAXBElement<CreateLocation> createCreateLocation(CreateLocation value) {
        return new JAXBElement<CreateLocation>(_CreateLocation_QNAME, CreateLocation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListCategories }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listCategories")
    public JAXBElement<ListCategories> createListCategories(ListCategories value) {
        return new JAXBElement<ListCategories>(_ListCategories_QNAME, ListCategories.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "getUserDetails")
    public JAXBElement<GetUserDetails> createGetUserDetails(GetUserDetails value) {
        return new JAXBElement<GetUserDetails>(_GetUserDetails_QNAME, GetUserDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListCities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listCities")
    public JAXBElement<ListCities> createListCities(ListCities value) {
        return new JAXBElement<ListCities>(_ListCities_QNAME, ListCities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "setUserDetails")
    public JAXBElement<SetUserDetails> createSetUserDetails(SetUserDetails value) {
        return new JAXBElement<SetUserDetails>(_SetUserDetails_QNAME, SetUserDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListCitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listCitiesResponse")
    public JAXBElement<ListCitiesResponse> createListCitiesResponse(ListCitiesResponse value) {
        return new JAXBElement<ListCitiesResponse>(_ListCitiesResponse_QNAME, ListCitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAllLocations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listAllLocations")
    public JAXBElement<ListAllLocations> createListAllLocations(ListAllLocations value) {
        return new JAXBElement<ListAllLocations>(_ListAllLocations_QNAME, ListAllLocations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListCategoriesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listCategoriesResponse")
    public JAXBElement<ListCategoriesResponse> createListCategoriesResponse(ListCategoriesResponse value) {
        return new JAXBElement<ListCategoriesResponse>(_ListCategoriesResponse_QNAME, ListCategoriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GiveRatingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "giveRatingResponse")
    public JAXBElement<GiveRatingResponse> createGiveRatingResponse(GiveRatingResponse value) {
        return new JAXBElement<GiveRatingResponse>(_GiveRatingResponse_QNAME, GiveRatingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListLocationsWithName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "listLocationsWithName")
    public JAXBElement<ListLocationsWithName> createListLocationsWithName(ListLocationsWithName value) {
        return new JAXBElement<ListLocationsWithName>(_ListLocationsWithName_QNAME, ListLocationsWithName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "deleteUserResponse")
    public JAXBElement<DeleteUserResponse> createDeleteUserResponse(DeleteUserResponse value) {
        return new JAXBElement<DeleteUserResponse>(_DeleteUserResponse_QNAME, DeleteUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLocationWithImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "createLocationWithImage")
    public JAXBElement<CreateLocationWithImage> createCreateLocationWithImage(CreateLocationWithImage value) {
        return new JAXBElement<CreateLocationWithImage>(_CreateLocationWithImage_QNAME, CreateLocationWithImage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetLocationDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "setLocationDetailsResponse")
    public JAXBElement<SetLocationDetailsResponse> createSetLocationDetailsResponse(SetLocationDetailsResponse value) {
        return new JAXBElement<SetLocationDetailsResponse>(_SetLocationDetailsResponse_QNAME, SetLocationDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "getUserDetailsResponse")
    public JAXBElement<GetUserDetailsResponse> createGetUserDetailsResponse(GetUserDetailsResponse value) {
        return new JAXBElement<GetUserDetailsResponse>(_GetUserDetailsResponse_QNAME, GetUserDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetLocationDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "setLocationDetails")
    public JAXBElement<SetLocationDetails> createSetLocationDetails(SetLocationDetails value) {
        return new JAXBElement<SetLocationDetails>(_SetLocationDetails_QNAME, SetLocationDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "logoutResponse")
    public JAXBElement<LogoutResponse> createLogoutResponse(LogoutResponse value) {
        return new JAXBElement<LogoutResponse>(_LogoutResponse_QNAME, LogoutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://noobservice.noob.de/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

}
