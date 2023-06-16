package sg.edu.nus.iss.server.models;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class EthHospitalReview extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b505f80546001600160a01b031916331790556106928061002e5f395ff3fe608060405234801561000f575f80fd5b506004361061004a575f3560e01c80633ca477681461004e5780638c4b773d14610063578063e83ddcea14610076578063edefe766146100a4575b5f80fd5b61006161005c3660046103b4565b6100ce565b005b61006161007136600461043b565b610188565b61008961008436600461043b565b6101bd565b60405161009b96959493929190610495565b60405180910390f35b5f546100b6906001600160a01b031681565b6040516001600160a01b03909116815260200161009b565b6001805480820182555f919091526005027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6018061010c8782610567565b5060018101859055600281016101228582610567565b5060038101805461ff00191661010060ff86160217905560048101829055600180547f821a323f4677136653f15fda78d2c7cd25ad40f1930d20929b64586701a0d3e09161016f91610623565b60405190815260200160405180910390a1505050505050565b5f6001828154811061019c5761019c610648565b5f9182526020909120600360059092020101805460ff191660011790555050565b600181815481106101cc575f80fd5b905f5260205f2090600502015f91509050805f0180546101eb906104e1565b80601f0160208091040260200160405190810160405280929190818152602001828054610217906104e1565b80156102625780601f1061023957610100808354040283529160200191610262565b820191905f5260205f20905b81548152906001019060200180831161024557829003601f168201915b50505050509080600101549080600201805461027d906104e1565b80601f01602080910402602001604051908101604052809291908181526020018280546102a9906104e1565b80156102f45780601f106102cb576101008083540402835291602001916102f4565b820191905f5260205f20905b8154815290600101906020018083116102d757829003601f168201915b505050506003830154600490930154919260ff8082169361010090920416915086565b634e487b7160e01b5f52604160045260245ffd5b5f82601f83011261033a575f80fd5b813567ffffffffffffffff8082111561035557610355610317565b604051601f8301601f19908116603f0116810190828211818310171561037d5761037d610317565b81604052838152866020858801011115610395575f80fd5b836020870160208301375f602085830101528094505050505092915050565b5f805f805f60a086880312156103c8575f80fd5b853567ffffffffffffffff808211156103df575f80fd5b6103eb89838a0161032b565b9650602088013595506040880135915080821115610407575f80fd5b506104148882890161032b565b935050606086013560ff8116811461042a575f80fd5b949793965091946080013592915050565b5f6020828403121561044b575f80fd5b5035919050565b5f81518084525f5b818110156104765760208185018101518683018201520161045a565b505f602082860101526020601f19601f83011685010191505092915050565b60c081525f6104a760c0830189610452565b87602084015282810360408401526104bf8188610452565b9515156060840152505060ff92909216608083015260a0909101529392505050565b600181811c908216806104f557607f821691505b60208210810361051357634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610562575f81815260208120601f850160051c8101602086101561053f5750805b601f850160051c820191505b8181101561055e5782815560010161054b565b5050505b505050565b815167ffffffffffffffff81111561058157610581610317565b6105958161058f84546104e1565b84610519565b602080601f8311600181146105c8575f84156105b15750858301515b5f19600386901b1c1916600185901b17855561055e565b5f85815260208120601f198616915b828110156105f6578886015182559484019460019091019084016105d7565b508582101561061357878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b8181038181111561064257634e487b7160e01b5f52601160045260245ffd5b92915050565b634e487b7160e01b5f52603260045260245ffdfea264697066735822122058c03d788ec17008c3257ecfec85c6732969d10335b97782d4c13fb2abeb34c864736f6c63430008140033";

    public static final String FUNC_MOH = "MOH";

    public static final String FUNC_ADDREVIEW = "addReview";

    public static final String FUNC_REVIEWS = "reviews";

    public static final String FUNC_VERIFYPATIENT = "verifyPatient";

    public static final Event REVIEWINDEX_EVENT = new Event("reviewIndex", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected EthHospitalReview(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EthHospitalReview(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EthHospitalReview(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EthHospitalReview(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ReviewIndexEventResponse> getReviewIndexEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REVIEWINDEX_EVENT, transactionReceipt);
        ArrayList<ReviewIndexEventResponse> responses = new ArrayList<ReviewIndexEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReviewIndexEventResponse typedResponse = new ReviewIndexEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ReviewIndexEventResponse> reviewIndexEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ReviewIndexEventResponse>() {
            @Override
            public ReviewIndexEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REVIEWINDEX_EVENT, log);
                ReviewIndexEventResponse typedResponse = new ReviewIndexEventResponse();
                typedResponse.log = log;
                typedResponse._index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ReviewIndexEventResponse> reviewIndexEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVIEWINDEX_EVENT));
        return reviewIndexEventFlowable(filter);
    }

    public RemoteFunctionCall<String> MOH() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MOH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addReview(String _facilityId, BigInteger _reviewId, String _patientNRIC, BigInteger _overallRating, BigInteger _hashedMessage) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDREVIEW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_facilityId), 
                new org.web3j.abi.datatypes.generated.Uint256(_reviewId), 
                new org.web3j.abi.datatypes.Utf8String(_patientNRIC), 
                new org.web3j.abi.datatypes.generated.Uint8(_overallRating), 
                new org.web3j.abi.datatypes.generated.Uint256(_hashedMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple6<String, BigInteger, String, Boolean, BigInteger, BigInteger>> reviews(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REVIEWS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<String, BigInteger, String, Boolean, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<String, BigInteger, String, Boolean, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, BigInteger, String, Boolean, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, BigInteger, String, Boolean, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> verifyPatient(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYPATIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EthHospitalReview load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthHospitalReview(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EthHospitalReview load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthHospitalReview(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EthHospitalReview load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EthHospitalReview(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EthHospitalReview load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EthHospitalReview(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EthHospitalReview> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EthHospitalReview.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<EthHospitalReview> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EthHospitalReview.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EthHospitalReview> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EthHospitalReview.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EthHospitalReview> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EthHospitalReview.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ReviewIndexEventResponse extends BaseEventResponse {
        public BigInteger _index;
    }
}
