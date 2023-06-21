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
    public static final String BINARY = "608060405234801562000010575f80fd5b50604051620011a5380380620011a5833981016040819052620000339162000086565b5f8054336001600160a01b031991821617909155600180549091166001600160a01b038416179055600262000069828262000200565b505050620002c8565b634e487b7160e01b5f52604160045260245ffd5b5f806040838503121562000098575f80fd5b82516001600160a01b0381168114620000af575f80fd5b602084810151919350906001600160401b0380821115620000ce575f80fd5b818601915086601f830112620000e2575f80fd5b815181811115620000f757620000f762000072565b604051601f8201601f19908116603f0116810190838211818310171562000122576200012262000072565b8160405282815289868487010111156200013a575f80fd5b5f93505b828410156200015d57848401860151818501870152928501926200013e565b5f8684830101528096505050505050509250929050565b600181811c908216806200018957607f821691505b602082108103620001a857634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115620001fb575f81815260208120601f850160051c81016020861015620001d65750805b601f850160051c820191505b81811015620001f757828155600101620001e2565b5050505b505050565b81516001600160401b038111156200021c576200021c62000072565b62000234816200022d845462000174565b84620001ae565b602080601f8311600181146200026a575f8415620002525750858301515b5f19600386901b1c1916600185901b178555620001f7565b5f85815260208120601f198616915b828110156200029a5788860151825594840194600190910190840162000279565b5085821015620002b857878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b610ecf80620002d65f395ff3fe608060405260043610610110575f3560e01c8063673ea0861161009d578063aa2fedde11610062578063aa2fedde14610308578063bc64a91a1461033e578063c0463711146103a3578063e83ddcea146103b8578063edefe766146103e8575f80fd5b8063673ea086146102815780636b87d24c146102965780637d952fb7146102b75780638c4b773d146102d557806393289602146102f4575f80fd5b806328b6e863116100e357806328b6e8631461018557806329899d79146101985780632de40ce31461021a5780634a1be6cb146102435780634a4b674a14610262575f80fd5b806302e4eb9a146101145780630d55e1b1146101465780630edd2ffc1461015c5780631e59a49814610171575b5f80fd5b34801561011f575f80fd5b5061013361012e366004610b1b565b610407565b6040519081526020015b60405180910390f35b348015610151575f80fd5b5061015a6104cb565b005b348015610167575f80fd5b5061013360075481565b34801561017c575f80fd5b50600554610133565b61015a610193366004610bf0565b610529565b3480156101a3575f80fd5b506101ac61078f565b60405161013d91905f61012082019050825182526020830151602083015260408301516040830152606083015160608301526080830151608083015260a083015160a083015260c083015160c083015260e083015160e0830152610100808401511515818401525092915050565b348015610225575f80fd5b506004546102339060ff1681565b604051901515815260200161013d565b34801561024e575f80fd5b5061015a61025d366004610c37565b61087c565b34801561026d575f80fd5b5061015a61027c366004610c37565b610897565b34801561028c575f80fd5b5061013360035481565b3480156102a1575f80fd5b506102aa6108b2565b60405161013d9190610c91565b3480156102c2575f80fd5b5060045461023390610100900460ff1681565b3480156102e0575f80fd5b5061015a6102ef366004610c37565b61093e565b3480156102ff575f80fd5b5061015a610988565b348015610313575f80fd5b505f54610326906001600160a01b031681565b6040516001600160a01b03909116815260200161013d565b348015610349575f80fd5b5061035d610358366004610c37565b6109ad565b60408051998a5260208a0198909852968801959095526060870193909352608086019190915260a085015260c084015260e083015215156101008201526101200161013d565b3480156103ae575f80fd5b5061013360065481565b3480156103c3575f80fd5b506103d76103d2366004610c37565b610a09565b60405161013d959493929190610caa565b3480156103f3575f80fd5b50600154610326906001600160a01b031681565b600880546001810182555f9182526003027ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee38101868155907ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee40161046b8682610d72565b5060028101805471ffffffffffffffffffffffffffffffffff00191661010060ff87160271ffffffffffffffffffffffffffffffff000019161762010000608086901c021790556008546104c190600190610e42565b9695505050505050565b6001546001600160a01b031633146104e1575f80fd5b6005545f906104f290600190610e42565b90505f6005828154811061050857610508610e5b565b5f9182526020909120600860099092020101805460ff191660011790555050565b5f546001600160a01b0316331461053e575f80fd5b6006545f9015610570576003546105589062015180610e6f565b6006546105659190610e86565b421115610570575060015b80156105bf57600754610584906001610e6f565b34106105bf576001546040515f916001600160a01b0316903480156108fc029184818181858888f19350505050905080156105bd575f91505b505b801561061c5760405162461bcd60e51b815260206004820152602260248201527f506c65617365207061792070656e616c747920666f72206c6174652075706461604482015261746560f01b606482015260840160405180910390fd5b50600580546001810182555f919091526009027f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db08101979097557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db18701959095557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db28601939093557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db38501919091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db48401557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db58301557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db6820155427f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db782018190557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db8909101805460ff19169055600655565b6107d76040518061012001604052805f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f81526020015f151581525090565b6005545f906107e890600190610e42565b9050600581815481106107fd576107fd610e5b565b5f91825260209182902060408051610120810182526009909302909101805483526001810154938301939093526002830154908201526003820154606082015260048201546080820152600582015460a0820152600682015460c0820152600782015460e082015260089091015460ff16151561010082015292915050565b6001546001600160a01b03163314610892575f80fd5b600355565b6001546001600160a01b031633146108ad575f80fd5b600755565b600280546108bf90610cec565b80601f01602080910402602001604051908101604052809291908181526020018280546108eb90610cec565b80156109365780601f1061090d57610100808354040283529160200191610936565b820191905f5260205f20905b81548152906001019060200180831161091957829003601f168201915b505050505081565b5f546001600160a01b03163314610953575f80fd5b5f6008828154811061096757610967610e5b565b5f9182526020909120600260039092020101805460ff191660011790555050565b6001546001600160a01b0316331461099e575f80fd5b6004805460ff19166001179055565b600581815481106109bc575f80fd5b5f9182526020909120600990910201805460018201546002830154600384015460048501546005860154600687015460078801546008909801549698509496939592949193909260ff1689565b60088181548110610a18575f80fd5b5f9182526020909120600390910201805460018201805491935090610a3c90610cec565b80601f0160208091040260200160405190810160405280929190818152602001828054610a6890610cec565b8015610ab35780601f10610a8a57610100808354040283529160200191610ab3565b820191905f5260205f20905b815481529060010190602001808311610a9657829003601f168201915b5050506002909301549192505060ff8082169161010081049091169062010000900460801b85565b634e487b7160e01b5f52604160045260245ffd5b803560ff81168114610aff575f80fd5b919050565b80356001600160801b031981168114610aff575f80fd5b5f805f8060808587031215610b2e575f80fd5b84359350602085013567ffffffffffffffff80821115610b4c575f80fd5b818701915087601f830112610b5f575f80fd5b813581811115610b7157610b71610adb565b604051601f8201601f19908116603f01168101908382118183101715610b9957610b99610adb565b816040528281528a6020848701011115610bb1575f80fd5b826020860160208301375f602084830101528097505050505050610bd760408601610aef565b9150610be560608601610b04565b905092959194509250565b5f805f805f805f60e0888a031215610c06575f80fd5b505085359760208701359750604087013596606081013596506080810135955060a0810135945060c0013592509050565b5f60208284031215610c47575f80fd5b5035919050565b5f81518084525f5b81811015610c7257602081850181015186830182015201610c56565b505f602082860101526020601f19601f83011685010191505092915050565b602081525f610ca36020830184610c4e565b9392505050565b85815260a060208201525f610cc260a0830187610c4e565b94151560408301525060ff9290921660608301526001600160801b03191660809091015292915050565b600181811c90821680610d0057607f821691505b602082108103610d1e57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610d6d575f81815260208120601f850160051c81016020861015610d4a5750805b601f850160051c820191505b81811015610d6957828155600101610d56565b5050505b505050565b815167ffffffffffffffff811115610d8c57610d8c610adb565b610da081610d9a8454610cec565b84610d24565b602080601f831160018114610dd3575f8415610dbc5750858301515b5f19600386901b1c1916600185901b178555610d69565b5f85815260208120601f198616915b82811015610e0157888601518255948401946001909101908401610de2565b5085821015610e1e57878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610e5557610e55610e2e565b92915050565b634e487b7160e01b5f52603260045260245ffd5b8082028115828204841417610e5557610e55610e2e565b80820180821115610e5557610e55610e2e56fea26469706673582212202d243c17f509475485b2b21bbbc577f7d8d024bf6cfcfcd738866338cade2b4264736f6c63430008140033";

    public static final String FUNC_MOH = "MOH";

    public static final String FUNC_ADDREVIEW = "addReview";

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

    public RemoteFunctionCall<TransactionReceipt> verifyStatistic() {
        final Function function = new Function(
                FUNC_VERIFYSTATISTIC, 
                Arrays.<Type>asList(), 
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
