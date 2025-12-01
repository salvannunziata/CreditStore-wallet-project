package progetti.restapitest.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progetti.restapitest.entities.Wallet;


@Repository
public interface WalletRepository extends JpaRepository <Wallet, Long>{
    
    Optional<Wallet> findById(Long id);
    Optional<Wallet> findByUserId(Long userId);
    
}
