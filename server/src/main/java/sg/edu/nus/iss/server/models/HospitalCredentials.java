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
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
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
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple9;
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
public class HospitalCredentials extends Contract {
    public static final String BINARY = "608060405234801562000010575f80fd5b506040516200124138038062001241833981016040819052620000339162000086565b5f8054336001600160a01b031991821617909155600180549091166001600160a01b038416179055600262000069828262000200565b505050620002c8565b634e487b7160e01b5f52604160045260245ffd5b5f806040838503121562000098575f80fd5b82516001600160a01b0381168114620000af575f80fd5b602084810151919350906001600160401b0380821115620000ce575f80fd5b818601915086601f830112620000e2575f80fd5b815181811115620000f757620000f762000072565b604051601f8201601f19908116603f0116810190838211818310171562000122576200012262000072565b8160405282815289868487010111156200013a575f80fd5b5f93505b828410156200015d57848401860151818501870152928501926200013e565b5f8684830101528096505050505050509250929050565b600181811c908216806200018957607f821691505b602082108103620001a857634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115620001fb575f81815260208120601f850160051c81016020861015620001d65750805b601f850160051c820191505b81811015620001f757828155600101620001e2565b5050505b505050565b81516001600160401b038111156200021c576200021c62000072565b62000234816200022d845462000174565b84620001ae565b602080601f8311600181146200026a575f8415620002525750858301515b5f19600386901b1c1916600185901b178555620001f7565b5f85815260208120601f198616915b828110156200029a5788860151825594840194600190910190840162000279565b5085821015620002b857878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b610f6b80620002d65f395ff3fe608060405260043610610131575f3560e01c80636b87d24c116100a8578063aa2fedde1161006d578063aa2fedde1461035c578063bc64a91a14610392578063c0463711146103f7578063e83ddcea1461040c578063edefe7661461043c578063f1a3a62b1461045b575f80fd5b80636b87d24c146102d65780637d952fb7146102f75780638c4b773d1461031557806393289602146103345780639a32d64114610348575f80fd5b80632de40ce3116100f95780632de40ce3146102275780634a1be6cb146102505780634a4b674a1461026f57806352e6c51f1461028e578063664e5881146102ad578063673ea086146102c1575f80fd5b80630edd2ffc146101355780631e59a4981461015d57806322533b6f1461017157806328b6e8631461019257806329899d79146101a5575b5f80fd5b348015610140575f80fd5b5061014a60075481565b6040519081526020015b60405180910390f35b348015610168575f80fd5b5060055461014a565b34801561017c575f80fd5b5061019061018b366004610bca565b61046f565b005b6101906101a0366004610c96565b61053b565b3480156101b0575f80fd5b506101b96107a1565b60405161015491905f61012082019050825182526020830151602083015260408301516040830152606083015160608301526080830151608083015260a083015160a083015260c083015160c083015260e083015160e0830152610100808401511515818401525092915050565b348015610232575f80fd5b506004546102409060ff1681565b6040519015158152602001610154565b34801561025b575f80fd5b5061019061026a366004610cdd565b61088e565b34801561027a575f80fd5b50610190610289366004610cdd565b6108a9565b348015610299575f80fd5b506101906102a8366004610cdd565b6108c4565b3480156102b8575f80fd5b5060085461014a565b3480156102cc575f80fd5b5061014a60035481565b3480156102e1575f80fd5b506102ea61090f565b6040516101549190610d37565b348015610302575f80fd5b5060045461024090610100900460ff1681565b348015610320575f80fd5b5061019061032f366004610cdd565b61099b565b34801561033f575f80fd5b506101906109e5565b348015610353575f80fd5b50610190610a0a565b348015610367575f80fd5b505f5461037a906001600160a01b031681565b6040516001600160a01b039091168152602001610154565b34801561039d575f80fd5b506103b16103ac366004610cdd565b610a31565b60408051998a5260208a0198909852968801959095526060870193909352608086019190915260a085015260c084015260e0830152151561010082015261012001610154565b348015610402575f80fd5b5061014a60065481565b348015610417575f80fd5b5061042b610426366004610cdd565b610a8d565b604051610154959493929190610d50565b348015610447575f80fd5b5060015461037a906001600160a01b031681565b348015610466575f80fd5b50610240610b5a565b600880546001810182555f919091526004027ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee38101858155907ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee4016104d48582610e0e565b5060028101805461ff00191661010060ff861602179055600381018290556008547f821a323f4677136653f15fda78d2c7cd25ad40f1930d20929b64586701a0d3e09061052390600190610ede565b60405190815260200160405180910390a15050505050565b5f546001600160a01b03163314610550575f80fd5b6006545f90156105825760035461056a9062015180610ef7565b6006546105779190610f0e565b421115610582575060015b80156105d157600754610596906001610ef7565b34106105d1576001546040515f916001600160a01b0316903480156108fc029184818181858888f19350505050905080156105cf575f91505b505b801561062e5760405162461bcd60e51b815260206004820152602260248201527f506c65617365207061792070656e616c747920666f72206c6174652075706461604482015261746560f01b606482015260840160405180910390fd5b50600580546001810182555f919091526009027f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db08101979097557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db18701959095557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db28601939093557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db38501919091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db48401557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db58301557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db6820155427f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db782018190557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db8909101805460ff19169055600655565b6107e96040518061012001604052805f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f151581525090565b6005545f906107fa90600190610ede565b90506005818154811061080f5761080f610f21565b5f91825260209182902060408051610120810182526009909302909101805483526001810154938301939093526002830154908201526003820154606082015260048201546080820152600582015460a0820152600682015460c0820152600782015460e082015260089091015460ff16151561010082015292915050565b6001546001600160a01b031633146108a4575f80fd5b600355565b6001546001600160a01b031633146108bf575f80fd5b600755565b6001546001600160a01b031633146108da575f80fd5b5f600582815481106108ee576108ee610f21565b5f9182526020909120600860099092020101805460ff191660011790555050565b6002805461091c90610d88565b80601f016020809104026020016040519081016040528092919081815260200182805461094890610d88565b80156109935780601f1061096a57610100808354040283529160200191610993565b820191905f5260205f20905b81548152906001019060200180831161097657829003601f168201915b505050505081565b5f546001600160a01b031633146109b0575f80fd5b5f600882815481106109c4576109c4610f21565b5f9182526020909120600260049092020101805460ff191660011790555050565b6001546001600160a01b031633146109fb575f80fd5b6004805460ff19166001179055565b6001546001600160a01b03163314610a20575f80fd5b6004805461ff001916610100179055565b60058181548110610a40575f80fd5b5f9182526020909120600990910201805460018201546002830154600384015460048501546005860154600687015460078801546008909801549698509496939592949193909260ff1689565b60088181548110610a9c575f80fd5b5f9182526020909120600490910201805460018201805491935090610ac090610d88565b80601f0160208091040260200160405190810160405280929190818152602001828054610aec90610d88565b8015610b375780601f10610b0e57610100808354040283529160200191610b37565b820191905f5260205f20905b815481529060010190602001808311610b1a57829003601f168201915b505050506002830154600390930154919260ff8082169361010090920416915085565b5f80600654118015610b6d57505f600354115b15610b9c57600354610b829062015180610ef7565b600654610b8f9190610f0e565b421115610b9c5750600190565b505f90565b634e487b7160e01b5f52604160045260245ffd5b803560ff81168114610bc5575f80fd5b919050565b5f805f8060808587031215610bdd575f80fd5b84359350602085013567ffffffffffffffff80821115610bfb575f80fd5b818701915087601f830112610c0e575f80fd5b813581811115610c2057610c20610ba1565b604051601f8201601f19908116603f01168101908382118183101715610c4857610c48610ba1565b816040528281528a6020848701011115610c60575f80fd5b826020860160208301375f602084830101528097505050505050610c8660408601610bb5565b9396929550929360600135925050565b5f805f805f805f60e0888a031215610cac575f80fd5b505085359760208701359750604087013596606081013596506080810135955060a0810135945060c0013592509050565b5f60208284031215610ced575f80fd5b5035919050565b5f81518084525f5b81811015610d1857602081850181015186830182015201610cfc565b505f602082860101526020601f19601f83011685010191505092915050565b602081525f610d496020830184610cf4565b9392505050565b85815260a060208201525f610d6860a0830187610cf4565b94151560408301525060ff92909216606083015260809091015292915050565b600181811c90821680610d9c57607f821691505b602082108103610dba57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610e09575f81815260208120601f850160051c81016020861015610de65750805b601f850160051c820191505b81811015610e0557828155600101610df2565b5050505b505050565b815167ffffffffffffffff811115610e2857610e28610ba1565b610e3c81610e368454610d88565b84610dc0565b602080601f831160018114610e6f575f8415610e585750858301515b5f19600386901b1c1916600185901b178555610e05565b5f85815260208120601f198616915b82811015610e9d57888601518255948401946001909101908401610e7e565b5085821015610eba57878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610ef157610ef1610eca565b92915050565b8082028115828204841417610ef157610ef1610eca565b80820180821115610ef157610ef1610eca565b634e487b7160e01b5f52603260045260245ffdfea26469706673582212203550413452d0066f079b0de393f7c186925c6d751cf379936cd36213523a93df64736f6c63430008140033";

    public static final String FUNC_MOH = "MOH";

    public static final String FUNC_ADDREVIEW = "addReview";

    public static final String FUNC_CHECKSTATISTICPASTDUE = "checkStatisticPastDue";

    public static final String FUNC_GETLATESTSTAT = "getLatestStat";

    public static final String FUNC_GETREVIEWSSIZE = "getReviewsSize";

    public static final String FUNC_GETSTATISTICSSIZE = "getStatisticsSize";

    public static final String FUNC_HOSPITAL = "hospital";

    public static final String FUNC_JCIACCREDITED = "jciAccredited";

    public static final String FUNC_LASTUPDATE = "lastUpdate";

    public static final String FUNC_LICENSE = "license";

    public static final String FUNC_PENALTY = "penalty";

    public static final String FUNC_REGISTERED = "registered";

    public static final String FUNC_REVIEWS = "reviews";

    public static final String FUNC_SETPENALTY = "setPenalty";

    public static final String FUNC_SETUPDATEFREQUENCY = "setUpdateFrequency";

    public static final String FUNC_STATISTICS = "statistics";

    public static final String FUNC_UPDATEFREQUENCY = "updateFrequency";

    public static final String FUNC_UPDATESTATISTIC = "updateStatistic";

    public static final String FUNC_VERIFYJCIACCREDITED = "verifyJciAccredited";

    public static final String FUNC_VERIFYLICENSE = "verifyLicense";

    public static final String FUNC_VERIFYPATIENT = "verifyPatient";

    public static final String FUNC_VERIFYSTATISTIC = "verifyStatistic";

    public static final Event REVIEWINDEX_EVENT = new Event("reviewIndex", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected HospitalCredentials(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HospitalCredentials(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HospitalCredentials(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HospitalCredentials(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<TransactionReceipt> addReview(BigInteger _reviewId, String _patientNRIC, BigInteger _overallRating, BigInteger _hashedMessage) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDREVIEW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_reviewId), 
                new org.web3j.abi.datatypes.Utf8String(_patientNRIC), 
                new org.web3j.abi.datatypes.generated.Uint8(_overallRating), 
                new org.web3j.abi.datatypes.generated.Uint256(_hashedMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> checkStatisticPastDue() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHECKSTATISTICPASTDUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Statistic> getLatestStat() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETLATESTSTAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Statistic>() {}));
        return executeRemoteCallSingleValueReturn(function, Statistic.class);
    }

    public RemoteFunctionCall<BigInteger> getReviewsSize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREVIEWSSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getStatisticsSize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSTATISTICSSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> hospital() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HOSPITAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> jciAccredited() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_JCIACCREDITED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> lastUpdate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LASTUPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> license() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LICENSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> penalty() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PENALTY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> registered() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REGISTERED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, Boolean, BigInteger, BigInteger>> reviews(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REVIEWS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, Boolean, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, String, Boolean, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, String, Boolean, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, Boolean, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setPenalty(BigInteger _wei) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPENALTY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_wei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUpdateFrequency(BigInteger _days) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETUPDATEFREQUENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_days)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> statistics(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STATISTICS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (Boolean) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> updateFrequency() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_UPDATEFREQUENCY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateStatistic(BigInteger _mortality, BigInteger _patientSafety, BigInteger _readmission, BigInteger _patientExperience, BigInteger _effectiveness, BigInteger _timeliness, BigInteger _medicalImagingEfficiency, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATESTATISTIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_mortality), 
                new org.web3j.abi.datatypes.generated.Uint256(_patientSafety), 
                new org.web3j.abi.datatypes.generated.Uint256(_readmission), 
                new org.web3j.abi.datatypes.generated.Uint256(_patientExperience), 
                new org.web3j.abi.datatypes.generated.Uint256(_effectiveness), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeliness), 
                new org.web3j.abi.datatypes.generated.Uint256(_medicalImagingEfficiency)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyJciAccredited() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYJCIACCREDITED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyLicense() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYLICENSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyPatient(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYPATIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyStatistic(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYSTATISTIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HospitalCredentials load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HospitalCredentials(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HospitalCredentials load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HospitalCredentials(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HospitalCredentials load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HospitalCredentials(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HospitalCredentials load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HospitalCredentials(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class Statistic extends StaticStruct {
        public BigInteger mortality;

        public BigInteger patientSafety;

        public BigInteger readmission;

        public BigInteger patientExperience;

        public BigInteger effectiveness;

        public BigInteger timeliness;

        public BigInteger medicalImagingEfficiency;

        public BigInteger timestamp;

        public Boolean verified;

        public Statistic(BigInteger mortality, BigInteger patientSafety, BigInteger readmission, BigInteger patientExperience, BigInteger effectiveness, BigInteger timeliness, BigInteger medicalImagingEfficiency, BigInteger timestamp, Boolean verified) {
            super(new org.web3j.abi.datatypes.generated.Uint256(mortality), 
                    new org.web3j.abi.datatypes.generated.Uint256(patientSafety), 
                    new org.web3j.abi.datatypes.generated.Uint256(readmission), 
                    new org.web3j.abi.datatypes.generated.Uint256(patientExperience), 
                    new org.web3j.abi.datatypes.generated.Uint256(effectiveness), 
                    new org.web3j.abi.datatypes.generated.Uint256(timeliness), 
                    new org.web3j.abi.datatypes.generated.Uint256(medicalImagingEfficiency), 
                    new org.web3j.abi.datatypes.generated.Uint256(timestamp), 
                    new org.web3j.abi.datatypes.Bool(verified));
            this.mortality = mortality;
            this.patientSafety = patientSafety;
            this.readmission = readmission;
            this.patientExperience = patientExperience;
            this.effectiveness = effectiveness;
            this.timeliness = timeliness;
            this.medicalImagingEfficiency = medicalImagingEfficiency;
            this.timestamp = timestamp;
            this.verified = verified;
        }

        public Statistic(Uint256 mortality, Uint256 patientSafety, Uint256 readmission, Uint256 patientExperience, Uint256 effectiveness, Uint256 timeliness, Uint256 medicalImagingEfficiency, Uint256 timestamp, Bool verified) {
            super(mortality, patientSafety, readmission, patientExperience, effectiveness, timeliness, medicalImagingEfficiency, timestamp, verified);
            this.mortality = mortality.getValue();
            this.patientSafety = patientSafety.getValue();
            this.readmission = readmission.getValue();
            this.patientExperience = patientExperience.getValue();
            this.effectiveness = effectiveness.getValue();
            this.timeliness = timeliness.getValue();
            this.medicalImagingEfficiency = medicalImagingEfficiency.getValue();
            this.timestamp = timestamp.getValue();
            this.verified = verified.getValue();
        }
    }

    public static class ReviewIndexEventResponse extends BaseEventResponse {
        public BigInteger _index;
    }
}
