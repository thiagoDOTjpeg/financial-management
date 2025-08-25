import { useEffect } from "react";
import { useAuthStore } from "../../store/authStore";

function StorageSynchronizer() {
  useEffect(() => {
    const handleStorageChange = (event: StorageEvent) => {
      if (event.key === "auth-storage") {
        useAuthStore.persist.rehydrate();
      }
    };

    window.addEventListener("storage", handleStorageChange);

    return () => {
      window.removeEventListener("storage", handleStorageChange);
    };
  }, []);

  return null;
}

export default StorageSynchronizer;
