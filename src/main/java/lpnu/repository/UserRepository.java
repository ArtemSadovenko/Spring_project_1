package lpnu.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import lpnu.dto.CryptoDTO;
import lpnu.dto.StockDTO;
import lpnu.dto.UserDTO;
import lpnu.entity.User;
import lpnu.entity.enumeration.Status;
import lpnu.entity.enumeration.UserRole;
import lpnu.exception.IrregularDate;
import lpnu.exception.RejectedPurchase;
import lpnu.mapper.UserMapper;
import lpnu.util.JacksonUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//import com.fasterxml.jackson.datatype;//jackson-datatype-jsr310;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private long id = 0L;
    private final LocalDate todayDate = LocalDate.now();
    private final double maxCustomerAmount = 10_000;


    public List<User> getAllUsers() {
//        User user = new User();
//        user.setName("Tom");
//        user.setSurname("Bond");
//        user.setBirthDate( LocalDate.of(2004, 1,1));
//        users.add(user);

        return new ArrayList<>(users);
    }

    public UserDTO delete(long id){
        final UserDTO userDTO = UserMapper.toFullDTO(findById(id));

        users = users.stream()
                .filter(e -> (e.getId() != id))
                .collect(Collectors.toList());

        return userDTO;
    }

    public void ban(long id){
        users = users.stream()
                .peek(e ->{
                    if(e.getId() == id){
                        e.setStatus(Status.INACTIVE);
                    }
                })
                .collect(Collectors.toList());
    }

    public void unban(long id){
        users = users.stream()
                .peek(e ->{
                    if(e.getId() == id){
                        e.setStatus(Status.ACTIVE);
                    }
                })
                .collect(Collectors.toList());
    }

//    public ResponseEntity save(User user){
//        try {
//            validate(user);
//            ++id;
//            user.setId(id);
//            users.add(user);
//
//        }catch (IrregularDate e){
//            return ResponseEntity.status(400).body(e.invoke());
//        }
//        return ResponseEntity.ok().body(user);
//    }

    public User save(User user) {

        validate(user);
        ++id;
        user.setId(id);
        user.setStatus(Status.ACTIVE);
        if (user.getUserRole() == null && user.getUserBalance() >= 100_000) {
            user.setUserRole(UserRole.VIP_CUSTOMER);
        } else if (user.getUserRole() == null) {
            user.setUserRole(UserRole.CUSTOMER);
        }
        users.add(user);

        return user;
    }

    private void validate(User user) throws IrregularDate {
        if (user.getBirthDate().isAfter(todayDate.minusYears(18))) {
            throw new IrregularDate("User is not adult", 400);
        }
        if (user.getUserBalance() < 0) {
            throw new IrregularDate("Balance is less then 0", 400);
        }
    }

    public User findById(Long id) {
        return users.stream()
                .filter(e -> e.getId() == (id))
                .findFirst()
                .orElseThrow(() -> new IrregularDate(("User not found by id: " + id)));
    }

    public User update(User user) {
        User saved = findById(user.getId());

        validate(user);
        saved.setName(user.getName());
        saved.setSurname(user.getSurname());
        saved.setUserRole(user.getUserRole());
        saved.setStatus(user.getStatus());
        saved.setUserBalance(user.getUserBalance());
        saved.setBriefcase(user.getBriefcase());

        if (saved.getUserBalance() >= 100_000 && saved.getUserRole() != UserRole.ADMIN) {
            saved.setUserRole(UserRole.VIP_CUSTOMER);
        }

        return saved;
    }

    public void addCrypto(CryptoDTO cryptoDTO, long userId) {
//        findById(userId);
//        users = users.stream()
//                .map(e -> {
//                    if(e.getId() == userId){
//                        if(e.getUserRole().equals(UserRole.CUSTOMER) && !(cryptoDTO.getAmount() > maxCustomerAmount)){
//                            throw new RejectedPurchase("User status is not VIP");
//                        }
//                        if(cryptoDTO.getAmount() * cryptoDTO.getCost() > e.getUserBalance()){
//                            throw new RejectedPurchase("User balance is not enough");
//                        }
//                        e.addCrypto(cryptoDTO);
//                    }
//                    return e;
//                })
//                .collect(Collectors.toList());
//        findById(userId);
        users = users.stream()
                .map(e -> {
                    if (e.getId() == userId) {
//                        if(e.getUserRole().equals(UserRole.CUSTOMER) && !(cryptoDTO.getAmount() > maxCustomerAmount)){
//                            throw new RejectedPurchase("User status is not VIP");
//                        }
//                        if(cryptoDTO.getAmount() * cryptoDTO.getCost() > e.getUserBalance()){
//                            throw new RejectedPurchase("User balance is not enough");
//                        }
                        e.addCrypto(cryptoDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public void validateCrypto(CryptoDTO cryptoDTO, long userId) {
        findById(userId);
        users.stream()
                .map(e -> {
                    if (e.getId() == userId) {
                        if(e.getStatus() == Status.INACTIVE){
                            throw new IrregularDate("User is banned");
                        }
                        if (e.getUserRole().equals(UserRole.CUSTOMER) && (cryptoDTO.getAmount() > maxCustomerAmount)) {
                            throw new RejectedPurchase("User status is not VIP");
                        }
                        if (cryptoDTO.getAmount() * cryptoDTO.getCost() > e.getUserBalance()) {
                            throw new RejectedPurchase("User balance is not enough");
                        }
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public void addStock(StockDTO stockDTO, long userId) {
        users = users.stream()
                .map(e -> {
                    if (e.getId() == userId) {
//                        if(e.getUserRole().equals(UserRole.CUSTOMER) && !(stockDTO.getAmount() > maxCustomerAmount)){
//                            throw new RejectedPurchase("User status is not VIP");
//                        }
//                        if(stockDTO.getAmount() * stockDTO.getCost() > e.getUserBalance()){
//                            throw new RejectedPurchase("User balance is not enough");
//                        }
                        e.addStock(stockDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public void validateStock(StockDTO stockDTO, long userId) {
        findById(userId);
        users.stream()
                .map(e -> {
                    if (e.getId() == userId) {
                        if(e.getStatus() == Status.INACTIVE){
                            throw new IrregularDate("User is banned");
                        }
                        if (e.getUserRole().equals(UserRole.CUSTOMER) && (stockDTO.getAmount() > maxCustomerAmount)) {
                            throw new RejectedPurchase("User status is not VIP");
                        }
                        if (stockDTO.getAmount() * stockDTO.getCost() > e.getUserBalance()) {
                            throw new RejectedPurchase("User balance is not enough");
                        }
                        //e.addCrypto(cryptoDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public CryptoDTO refreshCrypto(CryptoDTO cryptoDTO) {
        users = users.stream()
                .map(e -> {
                    if (e.getBriefcase() != null) {
                        e.refreshCrypto(cryptoDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return cryptoDTO;
    }

    public StockDTO refreshStock(StockDTO stockDTO){
        users = users.stream()
                .map(e -> {
                    if(e.getBriefcase() != null){
                        e.refreshStock(stockDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return stockDTO;
    }

    public void validateCryptoSell(CryptoDTO cryptoDTO, long userId) {
        findById(userId);
        users.stream()
                .map(e -> {
                    if (e.getId() == userId) {
                        if(e.getStatus() == Status.INACTIVE){
                            throw new IrregularDate("User is banned");
                        }
                        if (e.getUserRole().equals(UserRole.CUSTOMER) && (cryptoDTO.getAmount() > maxCustomerAmount)) {
                            throw new RejectedPurchase("User status is not VIP");
                        }
                        if(e.getBriefcase() == null){
                            throw new RejectedPurchase("There is nothing to sell");
                        }
                        e.getBriefcase().IsEnoughCrypto(cryptoDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public void validateStockSell(StockDTO stockDTO, long userId) {
        findById(userId);
        users.stream()
                .map(e -> {
                    if (e.getId() == userId) {
                        if(e.getStatus() == Status.INACTIVE){
                            throw new IrregularDate("User is banned");
                        }
                        if (e.getUserRole().equals(UserRole.CUSTOMER) && (stockDTO.getAmount() > maxCustomerAmount)) {
                            throw new RejectedPurchase("User status is not VIP");
                        }
                        if(e.getBriefcase() == null){
                            throw new RejectedPurchase("There is nothing to sell");
                        }
                        e.getBriefcase().IsEnoughStock(stockDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public CryptoDTO sellCrypto(CryptoDTO cryptoDTO, long userId){
        users = users.stream()
                .map(e -> {
                    if(e.getId() == userId){
                        e.sellCrypto(cryptoDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return cryptoDTO;
    }

    public StockDTO sellStock(StockDTO stockDTO, long userId){
        users = users.stream()
                .map(e -> {
                    if(e.getId() == userId){
                        e.sellStock(stockDTO);
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return stockDTO;
    }



    @PostConstruct
    public void init(){

        final Path file = Paths.get("users.txt");
        try {
            final String savedUsersAsString = Files.readString(file, StandardCharsets.UTF_16);
            users = JacksonUtil.deserialize(savedUsersAsString, new TypeReference<List<User>>() {});


            if (users == null) {
                users = new ArrayList<>();
                return;
            }

            this.id = users.stream().mapToLong(User::getId).max().orElse(0);



        } catch (final Exception e){
            System.out.println("We have an issue");
            users = new ArrayList<>();
        }

    }


    @PreDestroy
    public void preDestroy(){
        final Path file = Paths.get("users.txt");

        try {
            Files.writeString(file, JacksonUtil.serialize(users), StandardCharsets.UTF_16);
        } catch (final Exception e){
            System.out.println("We have an issue");
        }
    }
}
