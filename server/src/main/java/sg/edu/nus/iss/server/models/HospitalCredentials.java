package sg.edu.nus.iss.server.models;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
    public static final String BINARY = "608060405234801562000010575f80fd5b506040516200120338038062001203833981016040819052620000339162000086565b5f8054336001600160a01b031991821617909155600180549091166001600160a01b038416179055600262000069828262000200565b505050620002c8565b634e487b7160e01b5f52604160045260245ffd5b5f806040838503121562000098575f80fd5b82516001600160a01b0381168114620000af575f80fd5b602084810151919350906001600160401b0380821115620000ce575f80fd5b818601915086601f830112620000e2575f80fd5b815181811115620000f757620000f762000072565b604051601f8201601f19908116603f0116810190838211818310171562000122576200012262000072565b8160405282815289868487010111156200013a575f80fd5b5f93505b828410156200015d57848401860151818501870152928501926200013e565b5f8684830101528096505050505050509250929050565b600181811c908216806200018957607f821691505b602082108103620001a857634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115620001fb575f81815260208120601f850160051c81016020861015620001d65750805b601f850160051c820191505b81811015620001f757828155600101620001e2565b5050505b505050565b81516001600160401b038111156200021c576200021c62000072565b62000234816200022d845462000174565b84620001ae565b602080601f8311600181146200026a575f8415620002525750858301515b5f19600386901b1c1916600185901b178555620001f7565b5f85815260208120601f198616915b828110156200029a5788860151825594840194600190910190840162000279565b5085821015620002b857878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b610f2d80620002d65f395ff3fe60806040526004361061011b575f3560e01c80636b87d24c1161009d578063bc64a91a11610062578063bc64a91a14610354578063c0463711146103b9578063e83ddcea146103ce578063edefe766146103fe578063f1a3a62b1461041d575f80fd5b80636b87d24c146102ac5780637d952fb7146102cd5780638c4b773d146102eb578063932896021461030a578063aa2fedde1461031e575f80fd5b80632de40ce3116100e35780632de40ce3146102115780634a1be6cb1461023a5780634a4b674a1461025957806352e6c51f14610278578063673ea08614610297575f80fd5b806302e4eb9a1461011f5780630edd2ffc146101515780631e59a4981461016657806328b6e8631461017a57806329899d791461018f575b5f80fd5b34801561012a575f80fd5b5061013e610139366004610b79565b610431565b6040519081526020015b60405180910390f35b34801561015c575f80fd5b5061013e60075481565b348015610171575f80fd5b5060055461013e565b61018d610188366004610c4e565b6104f5565b005b34801561019a575f80fd5b506101a361075b565b60405161014891905f61012082019050825182526020830151602083015260408301516040830152606083015160608301526080830151608083015260a083015160a083015260c083015160c083015260e083015160e0830152610100808401511515818401525092915050565b34801561021c575f80fd5b5060045461022a9060ff1681565b6040519015158152602001610148565b348015610245575f80fd5b5061018d610254366004610c95565b610848565b348015610264575f80fd5b5061018d610273366004610c95565b610863565b348015610283575f80fd5b5061018d610292366004610c95565b61087e565b3480156102a2575f80fd5b5061013e60035481565b3480156102b7575f80fd5b506102c06108c9565b6040516101489190610cef565b3480156102d8575f80fd5b5060045461022a90610100900460ff1681565b3480156102f6575f80fd5b5061018d610305366004610c95565b610955565b348015610315575f80fd5b5061018d61099f565b348015610329575f80fd5b505f5461033c906001600160a01b031681565b6040516001600160a01b039091168152602001610148565b34801561035f575f80fd5b5061037361036e366004610c95565b6109c4565b60408051998a5260208a0198909852968801959095526060870193909352608086019190915260a085015260c084015260e0830152151561010082015261012001610148565b3480156103c4575f80fd5b5061013e60065481565b3480156103d9575f80fd5b506103ed6103e8366004610c95565b610a20565b604051610148959493929190610d08565b348015610409575f80fd5b5060015461033c906001600160a01b031681565b348015610428575f80fd5b5061022a610af2565b600880546001810182555f9182526003027ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee38101868155907ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee4016104958682610dd0565b5060028101805471ffffffffffffffffffffffffffffffffff00191661010060ff87160271ffffffffffffffffffffffffffffffff000019161762010000608086901c021790556008546104eb90600190610ea0565b9695505050505050565b5f546001600160a01b0316331461050a575f80fd5b6006545f901561053c576003546105249062015180610eb9565b6006546105319190610ed0565b42111561053c575060015b801561058b57600754610550906001610eb9565b341061058b576001546040515f916001600160a01b0316903480156108fc029184818181858888f1935050505090508015610589575f91505b505b80156105e85760405162461bcd60e51b815260206004820152602260248201527f506c65617365207061792070656e616c747920666f72206c6174652075706461604482015261746560f01b606482015260840160405180910390fd5b50600580546001810182555f919091526009027f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db08101979097557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db18701959095557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db28601939093557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db38501919091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db48401557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db58301557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db6820155427f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db782018190557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db8909101805460ff19169055600655565b6107a36040518061012001604052805f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f151581525090565b6005545f906107b490600190610ea0565b9050600581815481106107c9576107c9610ee3565b5f91825260209182902060408051610120810182526009909302909101805483526001810154938301939093526002830154908201526003820154606082015260048201546080820152600582015460a0820152600682015460c0820152600782015460e082015260089091015460ff16151561010082015292915050565b6001546001600160a01b0316331461085e575f80fd5b600355565b6001546001600160a01b03163314610879575f80fd5b600755565b6001546001600160a01b03163314610894575f80fd5b5f600582815481106108a8576108a8610ee3565b5f9182526020909120600860099092020101805460ff191660011790555050565b600280546108d690610d4a565b80601f016020809104026020016040519081016040528092919081815260200182805461090290610d4a565b801561094d5780601f106109245761010080835404028352916020019161094d565b820191905f5260205f20905b81548152906001019060200180831161093057829003601f168201915b505050505081565b5f546001600160a01b0316331461096a575f80fd5b5f6008828154811061097e5761097e610ee3565b5f9182526020909120600260039092020101805460ff191660011790555050565b6001546001600160a01b031633146109b5575f80fd5b6004805460ff19166001179055565b600581815481106109d3575f80fd5b5f9182526020909120600990910201805460018201546002830154600384015460048501546005860154600687015460078801546008909801549698509496939592949193909260ff1689565b60088181548110610a2f575f80fd5b5f9182526020909120600390910201805460018201805491935090610a5390610d4a565b80601f0160208091040260200160405190810160405280929190818152602001828054610a7f90610d4a565b8015610aca5780601f10610aa157610100808354040283529160200191610aca565b820191905f5260205f20905b815481529060010190602001808311610aad57829003601f168201915b5050506002909301549192505060ff8082169161010081049091169062010000900460801b85565b5f80600654118015610b0557505f600354115b15610b3457600354610b1a9062015180610eb9565b600654610b279190610ed0565b421115610b345750600190565b505f90565b634e487b7160e01b5f52604160045260245ffd5b803560ff81168114610b5d575f80fd5b919050565b80356001600160801b031981168114610b5d575f80fd5b5f805f8060808587031215610b8c575f80fd5b84359350602085013567ffffffffffffffff80821115610baa575f80fd5b818701915087601f830112610bbd575f80fd5b813581811115610bcf57610bcf610b39565b604051601f8201601f19908116603f01168101908382118183101715610bf757610bf7610b39565b816040528281528a6020848701011115610c0f575f80fd5b826020860160208301375f602084830101528097505050505050610c3560408601610b4d565b9150610c4360608601610b62565b905092959194509250565b5f805f805f805f60e0888a031215610c64575f80fd5b505085359760208701359750604087013596606081013596506080810135955060a0810135945060c0013592509050565b5f60208284031215610ca5575f80fd5b5035919050565b5f81518084525f5b81811015610cd057602081850181015186830182015201610cb4565b505f602082860101526020601f19601f83011685010191505092915050565b602081525f610d016020830184610cac565b9392505050565b85815260a060208201525f610d2060a0830187610cac565b94151560408301525060ff9290921660608301526001600160801b03191660809091015292915050565b600181811c90821680610d5e57607f821691505b602082108103610d7c57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610dcb575f81815260208120601f850160051c81016020861015610da85750805b601f850160051c820191505b81811015610dc757828155600101610db4565b5050505b505050565b815167ffffffffffffffff811115610dea57610dea610b39565b610dfe81610df88454610d4a565b84610d82565b602080601f831160018114610e31575f8415610e1a5750858301515b5f19600386901b1c1916600185901b178555610dc7565b5f85815260208120601f198616915b82811015610e5f57888601518255948401946001909101908401610e40565b5085821015610e7c57878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610eb357610eb3610e8c565b92915050565b8082028115828204841417610eb357610eb3610e8c565b80820180821115610eb357610eb3610e8c565b634e487b7160e01b5f52603260045260245ffdfea264697066735822122087d959981b1159ca0e5a0f09030b0d01810dc40d174bc703d203a08b811298c064736f6c63430008140033";

    public static final String FUNC_MOH = "MOH";

    public static final String FUNC_ADDREVIEW = "addReview";

    public static final String FUNC_CHECKSTATISTICPASTDUE = "checkStatisticPastDue";

    public static final String FUNC_GETLATESTSTAT = "getLatestStat";

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

    public static final String FUNC_VERIFYLICENSE = "verifyLicense";

    public static final String FUNC_VERIFYPATIENT = "verifyPatient";

    public static final String FUNC_VERIFYSTATISTIC = "verifyStatistic";

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

    public RemoteFunctionCall<String> MOH() {
        final Function function = new Function(FUNC_MOH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addReview(BigInteger _reviewId, String _patientNRIC, BigInteger _overallRating, byte[] _hashedMessage) {
        final Function function = new Function(
                FUNC_ADDREVIEW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_reviewId), 
                new org.web3j.abi.datatypes.Utf8String(_patientNRIC), 
                new org.web3j.abi.datatypes.generated.Uint8(_overallRating), 
                new org.web3j.abi.datatypes.generated.Bytes16(_hashedMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> checkStatisticPastDue() {
        final Function function = new Function(FUNC_CHECKSTATISTICPASTDUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Statistic> getLatestStat() {
        final Function function = new Function(FUNC_GETLATESTSTAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Statistic>() {}));
        return executeRemoteCallSingleValueReturn(function, Statistic.class);
    }

    public RemoteFunctionCall<BigInteger> getStatisticsSize() {
        final Function function = new Function(FUNC_GETSTATISTICSSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> hospital() {
        final Function function = new Function(FUNC_HOSPITAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> jciAccredited() {
        final Function function = new Function(FUNC_JCIACCREDITED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> lastUpdate() {
        final Function function = new Function(FUNC_LASTUPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> license() {
        final Function function = new Function(FUNC_LICENSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> penalty() {
        final Function function = new Function(FUNC_PENALTY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> registered() {
        final Function function = new Function(FUNC_REGISTERED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>> reviews(BigInteger param0) {
        final Function function = new Function(FUNC_REVIEWS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes16>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>>(function,
                new Callable<Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>>() {
                    @Override
                    public Tuple5<BigInteger, String, Boolean, BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setPenalty(BigInteger _wei) {
        final Function function = new Function(
                FUNC_SETPENALTY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_wei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUpdateFrequency(BigInteger _days) {
        final Function function = new Function(
                FUNC_SETUPDATEFREQUENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_days)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> statistics(BigInteger param0) {
        final Function function = new Function(FUNC_STATISTICS, 
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
        final Function function = new Function(FUNC_UPDATEFREQUENCY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateStatistic(BigInteger _mortality, BigInteger _patientSafety, BigInteger _readmission, BigInteger _patientExperience, BigInteger _effectiveness, BigInteger _timeliness, BigInteger _medicalImagingEfficiency, BigInteger weiValue) {
        final Function function = new Function(
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

    public RemoteFunctionCall<TransactionReceipt> verifyLicense() {
        final Function function = new Function(
                FUNC_VERIFYLICENSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyPatient(BigInteger _index) {
        final Function function = new Function(
                FUNC_VERIFYPATIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyStatistic(BigInteger _index) {
        final Function function = new Function(
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
}
