package pl.kalishak.trainclassif.registry;

public interface RegistryEntry<T> {

    int id();

    String getSimpleName();
    void setSimpleName(String simpleName);

    T getDefaults();
    void modifyDefaults(T modifyDefault);
}
