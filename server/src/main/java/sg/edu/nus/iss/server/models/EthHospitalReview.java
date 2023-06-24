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
    public static final String BINARY = "608060405234801561000f575f80fd5b505f80546001600160a01b031916331790556106bf8061002e5f395ff3fe608060405234801561000f575f80fd5b5060043610610055575f3560e01c80633ca4776814610059578063664e58811461006e5780638c4b773d14610084578063e83ddcea14610097578063edefe766146100bc575b5f80fd5b61006c6100673660046103e1565b6100e6565b005b6001546040519081526020015b60405180910390f35b61006c610092366004610468565b6101a0565b6100aa6100a5366004610468565b6101ea565b60405161007b969594939291906104c2565b5f546100ce906001600160a01b031681565b6040516001600160a01b03909116815260200161007b565b6001805480820182555f919091526005027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf601806101248782610594565b50600181018590556002810161013a8582610594565b5060038101805461ff00191661010060ff86160217905560048101829055600180547f821a323f4677136653f15fda78d2c7cd25ad40f1930d20929b64586701a0d3e09161018791610650565b60405190815260200160405180910390a1505050505050565b5f546001600160a01b031633146101b5575f80fd5b5f600182815481106101c9576101c9610675565b5f9182526020909120600360059092020101805460ff191660011790555050565b600181815481106101f9575f80fd5b905f5260205f2090600502015f91509050805f0180546102189061050e565b80601f01602080910402602001604051908101604052809291908181526020018280546102449061050e565b801561028f5780601f106102665761010080835404028352916020019161028f565b820191905f5260205f20905b81548152906001019060200180831161027257829003601f168201915b5050505050908060010154908060020180546102aa9061050e565b80601f01602080910402602001604051908101604052809291908181526020018280546102d69061050e565b80156103215780601f106102f857610100808354040283529160200191610321565b820191905f5260205f20905b81548152906001019060200180831161030457829003601f168201915b505050506003830154600490930154919260ff8082169361010090920416915086565b634e487b7160e01b5f52604160045260245ffd5b5f82601f830112610367575f80fd5b813567ffffffffffffffff8082111561038257610382610344565b604051601f8301601f19908116603f011681019082821181831017156103aa576103aa610344565b816040528381528660208588010111156103c2575f80fd5b836020870160208301375f602085830101528094505050505092915050565b5f805f805f60a086880312156103f5575f80fd5b853567ffffffffffffffff8082111561040c575f80fd5b61041889838a01610358565b9650602088013595506040880135915080821115610434575f80fd5b5061044188828901610358565b935050606086013560ff81168114610457575f80fd5b949793965091946080013592915050565b5f60208284031215610478575f80fd5b5035919050565b5f81518084525f5b818110156104a357602081850181015186830182015201610487565b505f602082860101526020601f19601f83011685010191505092915050565b60c081525f6104d460c083018961047f565b87602084015282810360408401526104ec818861047f565b9515156060840152505060ff92909216608083015260a0909101529392505050565b600181811c9082168061052257607f821691505b60208210810361054057634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561058f575f81815260208120601f850160051c8101602086101561056c5750805b601f850160051c820191505b8181101561058b57828155600101610578565b5050505b505050565b815167ffffffffffffffff8111156105ae576105ae610344565b6105c2816105bc845461050e565b84610546565b602080601f8311600181146105f5575f84156105de5750858301515b5f19600386901b1c1916600185901b17855561058b565b5f85815260208120601f198616915b8281101561062357888601518255948401946001909101908401610604565b508582101561064057878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b8181038181111561066f57634e487b7160e01b5f52601160045260245ffd5b92915050565b634e487b7160e01b5f52603260045260245ffdfea26469706673582212206cfcabc6c5f729183830fb9d58970927c0fe456fdeff4631b86e95efcaa09bbb64736f6c63430008140033";

    public static final String FUNC_MOH = "MOH";

    public static final String FUNC_ADDREVIEW = "addReview";

    public static final String FUNC_GETREVIEWSSIZE = "getReviewsSize";

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

    public RemoteFunctionCall<BigInteger> getReviewsSize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREVIEWSSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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
