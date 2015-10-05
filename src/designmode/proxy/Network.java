package designmode.proxy;

/**
 * 代理设计：由一个代理主题来操作真实主题，真实主题执行具体的业务操作，而代理主题负责其它相关业务的处理。
 */
public interface Network {

	public void browse();
}
