package progetti.restapitest.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progetti.restapitest.entities.Transaction;
import progetti.restapitest.entities.Wallet;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByWalletOrderByCreatedAtDesc(Wallet wallet);

}
